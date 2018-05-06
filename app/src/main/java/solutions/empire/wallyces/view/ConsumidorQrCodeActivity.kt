package solutions.empire.wallyces.view

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_consumidor_qr_code.*
import me.dm7.barcodescanner.core.CameraUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import solutions.empire.wallyces.R

class ConsumidorQrCodeActivity : AppCompatActivity(),ZXingScannerView.ResultHandler, EasyPermissions.PermissionCallbacks  {

    val REQUEST_CODE_CAMERA = 182

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumidor_qr_code)

        //val xXingScannerView = ZXingScannerView( this )

        askCameraPermission()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        /* Encaminhando resultados para EasyPermissions API */
        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this )
    }

    override fun onPermissionsDenied(
            requestCode: Int,
            perms: MutableList<String>) {

        askCameraPermission()
    }

    private fun askCameraPermission(){
        EasyPermissions.requestPermissions(
                PermissionRequest.Builder( this, REQUEST_CODE_CAMERA, android.Manifest.permission.CAMERA)
                        .setRationale( "A permissão de uso de câmera é necessária para que o aplicativo funcione." )
                        .setPositiveButtonText( "Ok" )
                        .setNegativeButtonText( "Cancelar" )
                        .build() )
    }

    override fun onPermissionsGranted(
            requestCode: Int,
            perms: MutableList<String>) {

        startCamera()

        /* AQUI SE INICIA A EXECUÇÃO DA BARCODE SCANNER API - ABERTURA DA CÂMERA */
    }

    override fun handleResult(result: Result?) {
        Log.e("clima de natal", "ny")
        Log.e("LOG", "Conteúdo do código lido: ${result!!.text}")
        Log.e("LOG", "Formato do código lido: ${result.barcodeFormat.name}")

        proccessBarcodeResult(
                result.text,
                result.barcodeFormat.name)

        //textView7.setText(result!!.text.toString())

        z_xing_scanner.resumeCameraPreview( this )
    }

    private fun startCamera(){
        if( EasyPermissions.hasPermissions( this, android.Manifest.permission.CAMERA ) ){
            z_xing_scanner.startCamera()
            z_xing_scanner.setAutoFocus(true)

        }
    }

    override fun onResume() {
        super.onResume()
        z_xing_scanner.setResultHandler(this)
        z_xing_scanner.startCamera()
        z_xing_scanner.setBorderColor(Color.RED)
        z_xing_scanner.setLaserColor(Color.YELLOW)
    }

    override fun onPause() {
        super.onPause()

        z_xing_scanner.stopCamera()

        val camera = CameraUtils.getCameraInstance()
        if( camera != null ){
            camera. release()
        }
    }

    private fun proccessBarcodeResult(
            text: String,
            barcodeFormatName: String ){

        val result = Result(
                text,
                text.toByteArray(),
                arrayOf(),
                BarcodeFormat.valueOf(barcodeFormatName))

        /* Modificando interface do usuário. */

        //titulo_prova_consumer.text = result.text

        Log.e("7", result.text.split("--").toString())

        titulo_consumer_value.text = result.text.split("--")[0]
        valor_prova_consumer_value.text = result.text.split("--")[1]
        dt_prova_consumer_value.text = result.text.split("--")[2]


        z_xing_scanner.resumeCameraPreview(this)
    }


}
