package com.sgwinkrace.utils


import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Base64
import android.util.Base64OutputStream
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.net.MalformedURLException
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


class Utils  {
    companion object {
        fun convertImageFileToBase64(imageFile: File): String {
            return ByteArrayOutputStream().use { outputStream ->
                Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                    imageFile.inputStream().use { inputStream ->
                        inputStream.copyTo(base64FilterStream)
                    }
                }
                return@use outputStream.toString()
            }
        }
        fun dateConverterTH(date: String): String {

            val formatter: SimpleDateFormat

            if (date == "") {
                return ""
            } else {

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                var testDate: Date? = null
                try {
                    testDate = sdf.parse(date)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                val date1 = testDate!!.date.toString()

                if (date1.endsWith("1") && !date1.endsWith("11"))
                    formatter = SimpleDateFormat("MMM d'st', yyyy h:mm a")
                else if (date1.endsWith("2") && !date1.endsWith("12"))
                    formatter = SimpleDateFormat("MMM d'nd', yyyy h:mm a")
                else if (date1.endsWith("3") && !date1.endsWith("13"))
                    formatter = SimpleDateFormat("MMM d'rd', yyyy h:mm a")
                else
                    formatter = SimpleDateFormat("MMM d'th', yyyy h:mm a")

                return formatter.format(testDate)
            }
        }

        fun dateConverter(date: String): String {

            val formatter: SimpleDateFormat

            if (date == "") {
                return ""
            } else {

                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                var testDate: Date? = null
                try {
                    testDate = sdf.parse(date)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                val date1 = testDate!!.date.toString()

                if (date1.endsWith("1") && !date1.endsWith("11"))
                    formatter = SimpleDateFormat("MMM d'st', yyyy h:mm a")
                else if (date1.endsWith("2") && !date1.endsWith("12"))
                    formatter = SimpleDateFormat("MMM d'nd', yyyy h:mm a")
                else if (date1.endsWith("3") && !date1.endsWith("13"))
                    formatter = SimpleDateFormat("MMM d'rd', yyyy h:mm a")
                else
                    formatter = SimpleDateFormat("MMM d'th', yyyy h:mm a")

                return formatter.format(testDate)
            }
        }

        fun dateConverterHistory(date: String): String {

            val formatter: SimpleDateFormat

            if (date == "") {
                return ""
            } else {

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                var testDate: Date? = null
                try {
                    testDate = sdf.parse(date)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                val date1 = testDate!!.date.toString()

                if (date1.endsWith("1") && !date1.endsWith("11"))
                    formatter = SimpleDateFormat("MMM d'st', yyyy")
                else if (date1.endsWith("2") && !date1.endsWith("12"))
                    formatter = SimpleDateFormat("MMM d'nd', yyyy")
                else if (date1.endsWith("3") && !date1.endsWith("13"))
                    formatter = SimpleDateFormat("MMM d'rd', yyyy")
                else
                    formatter = SimpleDateFormat("MMM d'th', yyyy")

                return formatter.format(testDate)
            }
        }

        fun dateConverterEvents(date: String): String {

            val formatter: SimpleDateFormat

            if (date == "") {
                return ""
            } else {

                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                var testDate: Date? = null
                try {
                    testDate = sdf.parse(date)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

                val date1 = testDate!!.date.toString()

                if (date1.endsWith("1") && !date1.endsWith("11"))
                    formatter = SimpleDateFormat("MMM d'st', yyyy'\n'h:mm a")
                else if (date1.endsWith("2") && !date1.endsWith("12"))
                    formatter = SimpleDateFormat("MMM d'nd', yyyy'\n'h:mm a")
                else if (date1.endsWith("3") && !date1.endsWith("13"))
                    formatter = SimpleDateFormat("MMM d'rd', yyyy'\n'h:mm a")
                else
                    formatter = SimpleDateFormat("MMM d'th', yyyy'\n'h:mm a")

                return formatter.format(testDate)
            }
        }

        fun getManagedTimeString(hours: Int, minutes: Int): String {
            var hours = hours

            var timeStr = ""
            var amPm = "AM"

            if (hours >= 12) {
                amPm = "PM"
                hours = hours - 12
            }
            if (hours == 0) {
                hours = 12
            }
            timeStr = (if (hours < 10) "0$hours" else hours).toString() + ":" + (if (minutes < 10) "0$minutes" else minutes) + " " + amPm

            return timeStr
        }

        fun getDisplayMetrics(context: Context): DisplayMetrics {

            val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(metrics)
            return metrics
        }

        fun getStringImage(bmp: Bitmap): String {

            val baos = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes = baos.toByteArray()

            //        int maxLogSize = 500;
            //        for (int i = 0; i <= (encodedImage.length() / maxLogSize); i++) {
            //            int start = i * maxLogSize;
            //            int end = (i + 1) * maxLogSize;
            //            end = end > encodedImage.length() ? encodedImage.length() : end;
            //            Log.d("mapData >>>", encodedImage.substring(start, end));
            //        }

            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }

        fun getImageFromStorage(path: String): Bitmap? {
            var f = File(path);
            var options = BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSizes(options, 2024, 2024);
            return BitmapFactory.decodeStream(FileInputStream(f), null, options)
        }
        fun calculateInSampleSizes(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 2

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

        fun  getRealPathFromURI(context: Context,contentURI: Uri): String {
            var result:String
            val cursor = context.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath()!!;
            } else {
                cursor.moveToFirst();
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return result;
        }

        fun  getUriFromBitmap(inContext:Context , inImage:Bitmap):Uri{
            val bytes =  ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }
        fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
            return Uri.parse(path)
        }

        //    public static void changeStatusBarColorTransparent(Activity activity) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            Window window = activity.getWindow();
        //            // clear FLAG_TRANSLUCENT_STATUS flag:
        //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //            // finally change the color
        //            window.setStatusBarColor(0x88000000);
        //        }
        //    }

        //    public static void changeStatusBarColorBlack(Activity activity) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            Window window = activity.getWindow();
        //            // clear FLAG_TRANSLUCENT_STATUS flag:
        //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //            // finally change the color
        //            window.setStatusBarColor(STATUS_BAR_COLOR_BLACK);
        //        }
        //    }

        fun isValidEmail(email: String): Boolean {

            val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun isConnectedToNetwork(context: Context): Boolean {

            val ConnectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = ConnectionManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        }

        fun dpToPx(context: Context, dp: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun pxToDp(context: Context, px: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun pixelsToSp(context: Context, px: Float): Float {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return px / scaledDensity
        }

        fun spToPixel(context: Context, sp: Int): Int {
            val scaledDensity = context.resources.displayMetrics.scaledDensity
            return (sp * scaledDensity).toInt()
        }

        fun getScaledBitmap(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeResource(res, resId, options)
        }

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }

            return inSampleSize
        }

        @Throws(MalformedURLException::class, PatternSyntaxException::class)
        fun extractYoutubeId(url: String): String? {

            var id: String? = null
            val query = URL(url).query
            if (query != null) {
                val param = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (row in param) {
                    val param1 = row.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (param1[0] == "v") {
                        id = param1[1]
                    }
                }
            } else {
                val param = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                id = param[param.size - 1]
            }

            return id
        }

        @Throws(MalformedURLException::class, PatternSyntaxException::class)
        fun extractVimeoId(url: String): String {

            val param = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            return param[param.size - 1]
        }

        fun RoundTo2Decimals(`val`: Double): Double {
            val df2 = DecimalFormat("###.##")
            return java.lang.Double.valueOf(df2.format(`val`))
        }


        fun Toaster(messege: String, context: Context) {
            Toast.makeText(context, messege, Toast.LENGTH_SHORT).show()
        }

        val dataSex: ArrayList<String>
            get() {

                val sexData = ArrayList<String>()
                sexData.add("Male")
                sexData.add("Female")
                return sexData
            }

        val dataCountry: ArrayList<String>
            get() {

                val countryData = ArrayList<String>()
                countryData.add("Canada")
                countryData.add("India")
                countryData.add("USA")
                return countryData
            }
    }

    //    public static ArrayList<HomeCategoryClass> getData() {
    //        ArrayList<HomeCategoryClass> homeCategoryClassesList = new ArrayList<>();
    //        homeCategoryClassesList.add(new HomeCategoryClass("Lawyers Dairy", R.mipmap.ic_launcher));
    //        homeCategoryClassesList.add(new HomeCategoryClass("Legal Assistance", R.mipmap.ic_launcher));
    //        homeCategoryClassesList.add(new HomeCategoryClass("Drafting", R.mipmap.ic_launcher));
    //        homeCategoryClassesList.add(new HomeCategoryClass("Lawyers at GPS", R.mipmap.ic_launcher));
    //
    //        return homeCategoryClassesList;
    //    }

}
