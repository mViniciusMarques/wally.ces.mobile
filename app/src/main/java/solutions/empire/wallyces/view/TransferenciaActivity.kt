package solutions.empire.wallyces.view

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_transferencia.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import solutions.empire.wallyces.R

class TransferenciaActivity : AppCompatActivity() {

    var dadosColetados = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia)

        val actionBar = supportActionBar
        actionBar!!.hide()

        this.preencherCamposComDadosColetados()
        this.obterDadosParaConverter()
        this.converterParaQrCode(dadosColetados)



    }

    private fun codificarTextoParaBase64(prova: String): String {
        val data = prova.reversed().toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    private fun decodificarTextoDeBase64(provaCodificada: String): String {
        val datasd = Base64.decode(provaCodificada.reversed(), Base64.DEFAULT)
        return String(datasd, charset("UTF-8"))
    }

    private fun preencherCamposComDadosColetados() {
        this.titulo_transferencia.text      = intent.getStringExtra("titulo_prova")
        this.valor_prova_transferencia.text = intent.getStringExtra("valor_prova")
        this.data_valor_transferencia.text  = intent.getStringExtra("data_prova")
    }

    private fun obterDadosParaConverter(){
        this.dadosColetados = intent.getStringExtra("titulo_prova")
                .plus("--")
                .plus(intent.getStringExtra("valor_prova"))
                .plus("--")
                .plus(intent.getStringExtra("data_prova"))
                .plus("--")
                .plus(intent.getStringExtra("aluno_destino"))
                .plus("--")
                .plus(intent.getStringExtra("descricao_prova"))
    }

    private fun converterParaQrCode(codigo: String) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(codigo, BarcodeFormat.QR_CODE, 512, 512)
            val width     = bitMatrix.width
            val height    = bitMatrix.height
            val bmp       = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            qr_code_transferencia.setImageBitmap(bmp)

        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }


}
