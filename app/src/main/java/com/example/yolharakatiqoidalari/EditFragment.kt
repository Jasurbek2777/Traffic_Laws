package com.example.yolharakatiqoidalari

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.CameraBottomSheetBinding
import com.example.yolharakatiqoidalari.databinding.FragmentAddSignBinding
import com.example.yolharakatiqoidalari.databinding.FragmentEditBinding
import com.example.yolharakatiqoidalari.db.MyDbHelper
import com.example.yolharakatiqoidalari.models.Sign
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.ByteArrayOutputStream

private const val ARG_PARAM1 = "param1"
lateinit var binding_edit: FragmentEditBinding
private lateinit var type_list: ArrayList<String>
private lateinit var my_db: MyDbHelper
private lateinit var signPicture: ByteArray
private lateinit var type1: String

class EditFragment : Fragment() {

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signPicture = ByteArray(0)
        type_list = ArrayList()
        type_list.add("Ogohlantiruvchi")
        type_list.add("Imtiyozli")
        type_list.add("Taqiqlovchi")
        type_list.add("Buyuruvchi")
        type_list.add("Axborot-ishora")
        type_list.add("Servis")
        type_list.add("Qo'shimcha axborot")
        my_db = MyDbHelper(requireContext())
        my_db = MyDbHelper(requireContext())
        val sign = param1?.toInt()?.let { my_db.getbyId(it) }
        type1 = sign?.sign_type.toString()
        signPicture= sign?.sign_image!!
        binding_edit = FragmentEditBinding.inflate(inflater, container, false)
        binding_edit.addSignType.setSelection(5)
        binding_edit.addSignName.setText(sign.sign_name)
        binding_edit.addSignDesc.setText(sign.sign_desc)
        val image = sign?.sign_image
        var bitmap = image?.size?.let { BitmapFactory.decodeByteArray(image, 0, it) }
        binding_edit.signPhoto.setImageBitmap(bitmap)

        binding_edit.addSignType.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, type_list)

        binding_edit.signPhoto.setOnClickListener {
            val bsh = CameraBottomSheetBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )
            val builder = BottomSheetDialog(requireContext())
            builder.setContentView(bsh.root)

            bsh.camera.setOnClickListener {
                getPichtureFromCamera()
                builder.dismiss()
            }
            bsh.folder.setOnClickListener {
                getPictureFromGalery()
                builder.dismiss()
            }
            builder.show()
        }

        binding_edit.saveBtn.setOnClickListener {
            val name = binding_edit.addSignName.text.toString()
            val desc = binding_edit.addSignDesc.text.toString()
            type1 = binding_edit.addSignType.selectedItem.toString()
            if (name.isNotEmpty() && desc.isNotEmpty()) {
                if (signPicture.isNotEmpty()) {
                    my_db.editSign(Sign(sign?.sign_id, name, desc, type1, signPicture, 0))
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Iltimos belgi rasmini kiriting",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Iltimos malumotlarni to'ldiring",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding_edit.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.back, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.menu.back -> {
                findNavController().popBackStack()
            }
        }
        return true
    }

    fun getPictureFromGalery() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    val REQUEST_CODE = 1
                    startActivityForResult(intent, REQUEST_CODE)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(requireContext(), "Permisson denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    fun getPichtureFromCamera() {

        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                @SuppressLint("ResourceType")
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 0)

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()

                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == -1) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding_edit.signPhoto.setImageBitmap(bitmap)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            signPicture = byteArray
        } else if (requestCode == 1 && resultCode == -1) {
            val uri = data?.data!!
            var bitmap: Bitmap?
            bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            binding_edit.signPhoto.setImageBitmap(bitmap)
            signPicture = byteArray
        }
    }
}