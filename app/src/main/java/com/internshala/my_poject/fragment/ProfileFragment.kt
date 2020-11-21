package com.internshala.my_poject.fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class ProfileFragment : Fragment() {

    lateinit var mDatabase : DatabaseReference
    lateinit var imgUserImage : ImageView
    lateinit var txtUserName : TextView
    lateinit var txtPhone : TextView
    lateinit var txtEmail : TextView
    lateinit var txtAddress : TextView
    val auth = FirebaseAuth.getInstance()
    lateinit var mAuth: FirebaseAuth
//    private val  REQUEST_IMAGE_CAPTURE = 100
//    private lateinit var imageuri : Uri
//    private var storageRef : Storage? =  null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View?{
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        imgUserImage.setOnClickListener {
//            takepictureIntent()
//        }
//    }
//
//    private fun takepictureIntent() {
//       Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
//           pictureIntent.resolveActivity(activity?.packageManager!!)?.also {
//               startActivityForResult(pictureIntent ,REQUEST_IMAGE_CAPTURE)
//           }
//       }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//             val imageBitmap = data?.extras?.get("data") as Bitmap
//            UploadImageandSaveUri(imageBitmap)
//        }
//    }
//
//    private fun UploadImageandSaveUri(bitmap: Bitmap) {
//        val baos = ByteArrayOutputStream()
//        var  StorageRef = FirebaseStorage.getInstance().
//        reference.child("pics/ ${FirebaseAuth.getInstance().currentUser?.uid}")
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , baos)
//        val image = baos.toByteArray()
//        val upload = StorageRef.putBytes(image)
//        upload.addOnCompleteListener { uploadTask ->
//            if (uploadTask.isSuccessful) {
//                StorageRef.downloadUrl.addOnCompleteListener { urlTask ->
//                    urlTask.result?.let {
//                        imageuri = it
////                        activity?.toast(imageuri.toString())
////                        imgUserImage.setImageBitmap(bitmap)
//                    }
//                }
//            }
//            else {
////                uploadTask.exception?.let {
////                    activity?.(it.message!!)
////                }
//                Toast.makeText(context, "exception occured" , Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}
