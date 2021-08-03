package com.example.yolharakatiqoidalari.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolharakatiqoidalari.R
import com.example.yolharakatiqoidalari.databinding.RvItemBinding
import com.example.yolharakatiqoidalari.models.Sign

class RvAdapter(var list: ArrayList<Sign>, var itemCLick: setOnItemCLick) :
    RecyclerView.Adapter<RvAdapter.RvVh>() {

    inner class RvVh(var itemBinding: RvItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(sign: Sign, position: Int) {
            itemBinding.rvItemName.setText(sign.sign_name)
            if (sign.sign_likable == 1) {
                itemBinding.rvItemLikeble.setImageResource(R.drawable.ic_liked)
            } else {
                itemBinding.rvItemLikeble.setImageResource(R.drawable.ic_disliked)
            }
            val image = sign.sign_image
            var bitmap = image?.size?.let { BitmapFactory.decodeByteArray(image, 0, it) }
            itemBinding.rvItemImage.setImageBitmap(bitmap)

            itemBinding.rvItemImage.setOnClickListener {
                itemCLick.itemOnCLick(sign, position)
            }
            itemBinding.rvItemEditBtn.setOnClickListener {
                itemCLick.itemOnEditCLick(sign, position)
            }
            itemBinding.rvItemDeleteBtn.setOnClickListener {
                itemCLick.itemOnDeleteCLick(sign, position)
            }
            itemBinding.rvItemLikeble.setOnClickListener {
                if (sign.sign_likable == 1) {
                    itemBinding.rvItemLikeble.setImageResource(R.drawable.ic_disliked)
                    sign.sign_likable=0
                    itemCLick.itemLikedClick(sign, position)
                } else {
                    itemCLick.itemLikedClick(sign, position)
                    itemBinding.rvItemLikeble.setImageResource(R.drawable.ic_liked)
                    sign.sign_likable=1
                }
                itemCLick.itemLikedClick(sign,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvVh = RvVh(
        RvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RvVh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
    interface setOnItemCLick {
        fun itemOnCLick(sign: Sign, position: Int)
        fun itemOnEditCLick(sign: Sign, position: Int)
        fun itemOnDeleteCLick(sign: Sign, position: Int)
        fun itemLikedClick(sign: Sign, position: Int)
    }
}