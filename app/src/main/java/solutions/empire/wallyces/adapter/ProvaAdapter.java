package solutions.empire.wallyces.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import solutions.empire.wallyces.R;
import solutions.empire.wallyces.interfaces.RecycleViewOnClickListenerHack;
import solutions.empire.wallyces.model.Prova;

/**
 * Created by mviniciusmarques on 29/04/18.
 */

public class ProvaAdapter extends RecyclerView.Adapter<ProvaAdapter.ProvaViewHolder> {

    private final Context context;
    private final List<Prova> provaCards;
    private RecycleViewOnClickListenerHack mRecycleViewOnClickListenerHack;

    public ProvaAdapter(Context context, List<Prova> provaCards) {
        this.context = context;
        this.provaCards = provaCards;
    }


    @Override
    public ProvaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inc_cartao_prova_layout, parent, false);
        return new ProvaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProvaViewHolder holder, int position) {
        Prova provaCard = provaCards.get(position);

        holder.titulo.setText(provaCard.getNomeProva());
        holder.descricao.setText(provaCard.getDescricaoProva());
        holder.valorProva.setText(provaCard.getValorProva().toString());
    }

    @Override
    public int getItemCount() {
        return this.provaCards != null ? this.provaCards.size() : 0;
    }

    public void setmRecycleViewOnClickListenerHack(RecycleViewOnClickListenerHack r) {
        mRecycleViewOnClickListenerHack = r;
    }

    public void removeItem(int position) {
        provaCards.remove(position);
        notifyItemRemoved(position);
    }

    public class ProvaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView titulo;
        public TextView descricao;
        public TextView valorProva;

        public ProvaViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.cartao_titulo_prova);
            descricao = itemView.findViewById(R.id.cartao_descricao_prova);
            valorProva = itemView.findViewById(R.id.cartao_valor_prova);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Prova clickedDataItem = provaCards.get(pos);
//                        Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getDescricaoProva(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//            });

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if(mRecycleViewOnClickListenerHack != null){
                Log.e("edward", "sharpe" );
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }

        }
    }
}
