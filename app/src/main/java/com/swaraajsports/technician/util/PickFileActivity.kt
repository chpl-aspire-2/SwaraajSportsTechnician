package com.swaraajsports.technician.util

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.anggrayudi.storage.file.openInputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class PickFileActivity : AppCompatActivity() {

    var waitResultForPhoto: ActivityResultLauncher<Intent>? = null

    lateinit var partValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        waitResultForPhoto = registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val documentsTree = DocumentFile.fromSingleUri(application, result.data!!.data!!)
                    VariableBag.partsFilePick = MultipartBody.Part.createFormData(
                        partValue, documentsTree!!.name,
                        documentsTree.openInputStream(this@PickFileActivity)!!.readBytes()
                            .toRequestBody(
                                "multipart/form-data".toMediaTypeOrNull()
                            )
                    )
                    val intent = Intent()
                    val url: String = documentsTree.uri.path.toString()
                    if (url.isNotEmpty() && url.contains(".")) {
                        val extension = url.substring(url.lastIndexOf("."))
                        if (extension == ".png" || extension == ".jpg" || extension == ".jpeg" || extension == ".pdf" || extension == ".doc" || extension == ".docx") {
                            intent.putExtra("filePath", documentsTree.uri.path)
                        }else{
                            intent.putExtra("filePath", documentsTree.uri.path+documentsTree.name)

                        }
                    }else{
                        intent.putExtra("filePath", documentsTree.uri.path+documentsTree.name)
                    }

                    intent.putExtra("fileName", documentsTree.name)
                    intent.putExtra("fileLength", documentsTree.length())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }else{
                finish()
            }
        }

        val b: Bundle? = intent.extras

        if (b != null) {

            partValue = b.getString("partValue", "")

            VariableBag.partsFilePick = null

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)



            if(b.getBoolean("isVideo",false)){
                intent.type = "video/*"

            }else{

                intent.type = "*/*"

                intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                    "application/pdf",  // .pdf
                    "image/*",  // .pdf
                    "application/vnd.oasis.opendocument.text",  // .odt
                    "text/plain" // .txt
                ))
            }


            waitResultForPhoto!!.launch(intent)

        }
        else {
            Toast.makeText(this, "missing parameter!", Toast.LENGTH_SHORT).show()
        }
    }

}