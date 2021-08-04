package com.example.fixawy.Client.AllPreviousQuestions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.ReplyForMyQuestion.ReplyForMyQuestionActivity;
import com.example.fixawy.Client.ReplyQuestions.AnswerActivity;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.squareup.picasso.Picasso;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

import java.util.ArrayList;

public class AllPreviousQuestionsAdapter extends RecyclerView.Adapter<AllPreviousQuestionsAdapter.PreviousQuestionItemViewHolder> {

    ArrayList<Questions> questions = new ArrayList<>();
    Context context;
    String phoneClient,clientName;

    public void clear() {
        questions.clear();
    }


    public void add(Questions ques){
        questions.add(ques);
        notifyDataSetChanged();
    }

    public AllPreviousQuestionsAdapter(Context context, String phoneClient, String clientName) {
        this.context = context;
        this.phoneClient = phoneClient;
        this.clientName = clientName;
    }

    @NonNull
    @Override
    public AllPreviousQuestionsAdapter.PreviousQuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_question_item,null,false);
        AllPreviousQuestionsAdapter.PreviousQuestionItemViewHolder viewHolder = new AllPreviousQuestionsAdapter.PreviousQuestionItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllPreviousQuestionsAdapter.PreviousQuestionItemViewHolder holder, int position) {
        holder.textViewPhone.setText(questions.get(position).getPhone());
        holder.textViewQuestion.setText(questions.get(position).getQuestion());
        Picasso.get().load(questions.get(position).getImageUri()).into(holder.imageViewForQuestion);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobTitle = questions.get(position).getJobTitle();
                String phone = holder.textViewPhone.getText().toString();

                Toast.makeText(v.getContext(),phone +"and" + jobTitle, Toast.LENGTH_SHORT).show();

                String question = holder.textViewQuestion.getText().toString();
                Toast.makeText(v.getContext(),phone +"and" + jobTitle, Toast.LENGTH_SHORT).show();

                v.getContext().startActivity(new Intent(v.getContext(), ReplyForMyQuestionActivity.class)
                        .putExtra("phone",phone)
                        .putExtra("jobTitle",jobTitle)
                        .putExtra("question",question)
                        .putExtra(EXTR_USER_NAME,clientName));
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class PreviousQuestionItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion,textViewPhone;
        ImageView imageViewForQuestion;
        public View layout;
        public PreviousQuestionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            textViewPhone = itemView.findViewById(R.id.phone);
            textViewQuestion = itemView.findViewById(R.id.question);
            imageViewForQuestion=itemView.findViewById(R.id.imageFromQuestion);

        }
    }
}

