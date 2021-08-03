package com.example.yolharakatiqoidalari

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yolharakatiqoidalari.databinding.CameraBottomSheetBinding
import com.example.yolharakatiqoidalari.databinding.FragmentAddSignBinding
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


private  lateinit var type_list22: ArrayList<String>
private lateinit var my_db: MyDbHelper
private lateinit var signPicture: ByteArray
private lateinit var bindingAdd: FragmentAddSignBinding
private lateinit var type1: String

class AddSignFragment : Fragment() {
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signPicture= ByteArray(0)
        type_list22 = ArrayList()
        type1 = "Ogohlantiruvchi"
        type_list22.add("Ogohlantiruvchi")
        type_list22.add("Imtiyozli")
        type_list22.add("Taqiqlovchi")
        type_list22.add("Buyuruvchi")
        type_list22.add("Axborot-ishora")
        type_list22.add("Servis")
        type_list22.add("Qo'shimcha axborot")
        my_db = MyDbHelper(requireContext())
        bindingAdd = FragmentAddSignBinding.inflate(inflater, container, false)
        bindingAdd.addSignType.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, type_list22)

        bindingAdd.signPhoto.setOnClickListener {
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

        bindingAdd.saveBtn.setOnClickListener {
            val name = bindingAdd.addSignName.text.toString()
            val desc = bindingAdd.addSignDesc.text.toString()
            type1 = bindingAdd.addSignType.selectedItem.toString()
            if (name.isNotEmpty() && desc.isNotEmpty()) {
                if (signPicture.isNotEmpty()) {
                    my_db.addSign(Sign(name, desc, type1, signPicture, 0))
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.homeFragment)
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




        return bindingAdd.root
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
            bindingAdd.signPhoto.setImageBitmap(bitmap)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            signPicture = byteArray
        } else if (requestCode == 1 && resultCode == -1) {
            val uri = data?.data!!
            var bitmap: Bitmap? = null
            bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            bindingAdd.signPhoto.setImageBitmap(bitmap)
            signPicture = byteArray
        }
    }


}