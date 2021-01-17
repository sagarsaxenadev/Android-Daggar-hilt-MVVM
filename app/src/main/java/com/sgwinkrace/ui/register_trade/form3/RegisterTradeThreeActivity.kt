package com.sgwinkrace.ui.register_trade.form3

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sgwinkrace.databinding.ActivityRegisterTradeThreeBinding
import kotlinx.android.synthetic.main.header_register_trade_layout.*
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.sgwinkrace.datastore.AppDataStore
import com.sgwinkrace.ui.register_trade.form2.RegisterTradeTwoActivity
import com.sgwinkrace.ui.register_trade.form4.RegisterTradeFourActivity
import com.sgwinkrace.ui.register_trade.viewmodel.RegisterTradeOneViewModel
import com.sgwinkrace.utils.Utils
import com.sgwinkrace.utils.hideProgressDialog
import com.sgwinkrace.utils.showProgressDialog
import com.sgwinkrace.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

const val AADHHAAR_FRONT = 1000
const val AADHHAAR_BACK = 1001
const val PAN_CARD = 2000
const val GST = 3000

@AndroidEntryPoint
class RegisterTradeThreeActivity : AppCompatActivity() {

    private var imageAadhaarCardFront = ""
    private var imageGST = ""
    private var imageAadhaarCardback = ""
    private var imagePanCard = ""
    lateinit var binding: ActivityRegisterTradeThreeBinding

    var mTradeId=""

    @Inject
    lateinit var appDataStore: AppDataStore
    var userId = ""

    val viewModel : RegisterTradeOneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTradeThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTradeId=intent.getStringExtra("trade_id") ?:""

        tvHeaderTitle.text = "Register Trade"
        imgHeaderBack.setOnClickListener { finish() }
        tvStepNumber.text = "Step 3/4"

        onClickHandler()
    }

    private fun onClickHandler() {
        binding.imgAadhaarFront.setOnClickListener {
            ImagePicker.create(this)
                .single()
                .toolbarImageTitle("Choose Aadhaar Front")
                .start(AADHHAAR_FRONT);
        }

        binding.imgAadhaarBack.setOnClickListener {
            ImagePicker.create(this)
                .single()
                .toolbarImageTitle("Choose Aadhaar Back")
                .start(AADHHAAR_BACK);
        }


        binding.imgGST.setOnClickListener {
            ImagePicker.create(this)
                .single()
                .toolbarImageTitle("Choose GST")
                .start(GST);
        }

        binding.imgPanCard.setOnClickListener {
            ImagePicker.create(this)
                .single()
                .toolbarImageTitle("Choose Pan Card")
                .start(PAN_CARD);
        }

        binding.btnNext.setOnClickListener {
            when{

                imageAadhaarCardFront.isNullOrEmpty() -> snackBar(it,"Select aadhaar card")
                imagePanCard.isNullOrEmpty() -> snackBar(it,"Select aadhaar card")

                else -> {

                    lifecycleScope.launchWhenStarted {

                        appDataStore.userIdFlow.collect {
                            userId = it
                        }
                    }

                    showProgressDialog(this,"Please wait...")

                    var jsonArrayImages=JSONArray()




                    val objPan1 = JSONObject()
                    objPan1.put("name","pan")
                    objPan1.put("document",imagePanCard)
                    jsonArrayImages.put(objPan1)

                    val objAadhaar = JSONObject()
                    objAadhaar.put("name","adhaar")
                    objAadhaar.put("document",imageAadhaarCardFront)
                    jsonArrayImages.put(objAadhaar)



                    Log.d("images json -> ",jsonArrayImages.toString())
                    viewModel.tradeStep3(userId,mTradeId,jsonArrayImages.toString())

                    viewModel.tradeStep3LiveData.observe(this,{ response ->
                        hideProgressDialog()
                        if (response.sTATUS == "SUCCESS") {

                            Intent(this, RegisterTradeFourActivity::class.java).apply {
                                putExtra("trade_id",response.iD)
                                startActivity(this)
                            }

                        }else {
                            snackBar(it,response.mESSAGE)
                        }

                    })

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (ImagePicker.getImages(data).isNullOrEmpty()) {

        } else {
            if (requestCode == AADHHAAR_FRONT) {
                var image = ImagePicker.getFirstImageOrNull(data).path
                val filePath: File = File(image)
                Glide.with(this).load(filePath).into(binding.imgAadhaarFront)
                imageAadhaarCardFront = Utils.convertImageFileToBase64(filePath)

            }

            if (requestCode == AADHHAAR_BACK) {
                var image = ImagePicker.getFirstImageOrNull(data).path
                val filePath: File = File(image)
                Glide.with(this).load(filePath).into(binding.imgAadhaarBack)

                imageAadhaarCardback = Utils.convertImageFileToBase64(filePath)

            }


            if (requestCode == PAN_CARD) {
                var image = ImagePicker.getFirstImageOrNull(data).path
                val filePath: File = File(image)
                Glide.with(this).load(filePath).into(binding.imgPanCard)

                imagePanCard = Utils.convertImageFileToBase64(filePath)
            }

            if (requestCode == GST) {
                var image = ImagePicker.getFirstImageOrNull(data).path
                val filePath: File = File(image)
                Glide.with(this).load(filePath).into(binding.imgGST)

                imageGST = Utils.convertImageFileToBase64(filePath)

            }

        }
    }

}