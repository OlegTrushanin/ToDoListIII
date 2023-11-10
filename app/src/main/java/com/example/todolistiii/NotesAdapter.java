package com.example.todolistiii;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private List<Note> notes = new ArrayList<>();



//    private OnNoteClickListener onNoteClickListener;

    public List<Note> getNotes() {
        return notes;
    }

//    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
//        this.onNoteClickListener = onNoteClickListener;
//    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // с помощью этого метода делаем из XML View. Метод возвращает объет NotesViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(  // переводим макет XML во View
                R.layout.item,  // макет который переводим
                parent, // куда (в какой контейнер) будем добавлять
                false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {

        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());

        int colorResId;

        switch(note.getPriority()){
            case 0:
                colorResId =android.R.color.holo_green_light;
                break;
            case 1:
                colorResId = android.R.color.holo_orange_dark;
                break;
            default:
                colorResId = android.R.color.holo_red_dark;
        }

        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);

        viewHolder.textViewNote.setBackgroundColor(color);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(onNoteClickListener != null) {
//                    onNoteClickListener.onNoteClick(note);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

//    interface OnNoteClickListener{
//
//        void onNoteClick(Note note);
//
//    }

    public class NotesViewHolder extends RecyclerView.ViewHolder { // в этом классе будут определяться и храниться ссылки на textViewNote

        TextView textViewNote;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

}

