package com.hansung.traveldiary.src.profile.gallery

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class ImageGallery {
    companion object{
        fun  listOfImages(context: Context) : ArrayList<String>{
            val uri : Uri
            val cursor: Cursor

            val column_index_data: Int
            var column_index_folder_name: Int
            val listOfAllImages = ArrayList<String>()
            var ablosutePathOfImage: String
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            val orderBy = MediaStore.Video.Media.DATE_TAKEN
            cursor = context.contentResolver.query(
                uri, projection, null,
                null, "$orderBy DESC"
            )!!

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            //get folder name
            //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            while (cursor.moveToNext()) {
                ablosutePathOfImage = cursor.getString(column_index_data)
                listOfAllImages.add(ablosutePathOfImage)
            }

            return listOfAllImages
        }
    }

}