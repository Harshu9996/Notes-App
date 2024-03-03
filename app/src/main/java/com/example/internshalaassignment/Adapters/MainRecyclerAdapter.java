package com.example.internshalaassignment.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshalaassignment.Data.DAO.DAO;
import com.example.internshalaassignment.Data.DataModels.Note;
import com.example.internshalaassignment.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private List<Note> localDataSet;
    private NavController navController;

    private DAO notesDao;

    private Note deletedNote;
    private Context context;
    public MainRecyclerAdapter(DAO notesDao,Context context){
        this.notesDao = notesDao;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler_item_ui, parent, false);
        navController = Navigation.findNavController(parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTextTitle().setText(localDataSet.get(position).title);
        holder.getTxtDescription().setText(localDataSet.get(position).description);
        holder.getImgEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(context.getString(R.string.notePassingId),localDataSet.get(position));
                navController.navigate(R.id.action_mainFragment_to_editNoteFragment,bundle);
            }
        });
        holder.getImgDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesDao.delete(localDataSet.get(position));
                deletedNote = localDataSet.get(position);
                localDataSet.remove(position);
                setData(localDataSet);
                notifyDataSetChanged();

                Snackbar.make(holder.itemView, context.getString(R.string.note_deleted_message), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                notesDao.insert(deletedNote);
                                localDataSet.add(position,deletedNote);
                                setData(localDataSet);
                                notifyDataSetChanged();
                                deletedNote = null;

                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setData(List<Note> dataSet) {
        localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle,textDescription;
        private final ImageView imgEdit,imgDelete;

        public ViewHolder(View view) {
            super(view);

            textTitle = (TextView) view.findViewById(R.id.noteTitle);
            textDescription = (TextView) view.findViewById(R.id.noteDescription);
            imgEdit = (ImageView) view.findViewById(R.id.noteEdit);
            imgDelete = (ImageView) view.findViewById(R.id.noteDelete);
        }

        public TextView getTextTitle() {
            return textTitle;
        }
        public TextView getTxtDescription(){ return textDescription; }

        public ImageView getImgEdit(){
            return imgEdit;
        }

        public ImageView getImgDelete(){
            return imgDelete;
        }
    }

}
