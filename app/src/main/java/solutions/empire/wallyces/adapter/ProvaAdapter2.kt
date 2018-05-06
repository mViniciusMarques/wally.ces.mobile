package solutions.empire.wallyces.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.inc_prova_destaque.view.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.R.id.titulo_prova_destaque

import solutions.empire.wallyces.model.Prova

/**
 * Created by mviniciusmarques on 05/05/18.
 */
class ProvaAdapter2(applicationContext: Context, provas: MutableList<Prova>) : RecyclerView.Adapter<ProvaAdapter2.ProvaViewHolder>() {

    private var context: Context? = null
    private var provaCards: List<Prova>? = null

    fun ProvaAdapter2(context: Context, provaCards: List<Prova>) {
        this.context = context
        this.provaCards = provaCards
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.inc_cartao_prova_layout, parent, false)
        return ProvaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvaViewHolder, position: Int) {
        val provaCard = provaCards!![position]

        holder.titulo.text = provaCard.nomeProva
        holder.descricao.text = provaCard.descricaoProva
        //   holder.valorProva.setText(provaCard.getValorProva());

    }

    override fun getItemCount(): Int {
        return if (this.provaCards != null) this.provaCards!!.size else 0
    }

    inner class ProvaViewHolder
    //  public TextView valorProva;

    (itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titulo: TextView
        var descricao: TextView
        var das: TextView



        init {

           var provaView: View =  LayoutInflater.from(context).inflate(R.layout.inc_prova_destaque, null, false)

            titulo = itemView.findViewById(R.id.cartao_titulo_prova)
            descricao = itemView.findViewById(R.id.cartao_descricao_prova)
            // valorProva = itemView.findViewById(R.id.cartao_valor_prova);
            das = provaView.findViewById(R.id.titulo_prova_destaque);

            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val clickedDataItem = provaCards!![pos]
                    Toast.makeText(view.context, "You clicked " + clickedDataItem.descricaoProva, Toast.LENGTH_SHORT).show()
                     das.setText("cansei")

                }
            }

        }


    }

}