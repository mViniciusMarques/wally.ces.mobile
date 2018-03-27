package solutions.empire.wallyces.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import solutions.empire.wallyces.R;
import solutions.empire.wallyces.model.AvisoCard;

/**
 * Created by mvmarques2 on 26/03/2018.
 */

public class AvisoAdapter extends RecyclerView.Adapter<AvisoAdapter.AvisoViewHolder> {

    private final Context context;
    private final List<AvisoCard> avisoCards;

    public AvisoAdapter(Context context, List<AvisoCard> avisoCards) {
        this.context = context;
        this.avisoCards = avisoCards;
    }


    @Override
    public AvisoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inc_conteudo_aviso_card, parent, false);
        return new AvisoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvisoViewHolder holder, int position) {
        AvisoCard avisoCard = avisoCards.get(position);

        holder.titulo.setText(avisoCard.getTitulo() );
        holder.descricao.setText(avisoCard.getDescricao());
        holder.professor_nome.setText(avisoCard.getProfessor());
        holder.dataProfessor.setText(avisoCard.getDataCriacao());
    }

    @Override
    public int getItemCount() {
        return this.avisoCards != null ? this.avisoCards.size() : 0;
    }

    public class AvisoViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo;
        public TextView descricao;
        public TextView professor_nome;
        public TextView dataProfessor;

        public AvisoViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo_card_aviso);
            descricao = itemView.findViewById(R.id.descricao_card_aviso);
            professor_nome = itemView.findViewById(R.id.descricao_card_professor);
            dataProfessor = itemView.findViewById(R.id.descricao_card_data);
        }
    }
}
