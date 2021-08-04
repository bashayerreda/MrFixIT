package com.example.fixawy.Worker.WorkerQuestions;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.ReplyQuestions.AnswerActivity;
import com.example.fixawy.Pojos.Answer;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Worker.ReplayQuestionsPage.ReplayQuestionActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class WorkerQuestionsAdapter extends RecyclerView.Adapter<WorkerQuestionsAdapter.PreviousQuestionItemViewHolder> {

    ArrayList<Questions> questions = new ArrayList<>();
    Answer answerModel;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btnAddReplyDialog,btnCancelDialog;
    EditText addReplyText;
    DatabaseReference databaseReference;
    FirebaseDatabase db ;
    String reply,jobTitle,phoneClient,workerName;

    Context context;
    String phoneWorker;

    public WorkerQuestionsAdapter(WorkerQuestionsActivity workerQuestionsActivity, String phoneWorker,String workerName) {
        this.context = workerQuestionsActivity;
        this.phoneWorker = phoneWorker;
        this.workerName = workerName;

    }


    public void clear() {
        questions.clear();
    }


    public void add(Questions ques){
        questions.add(ques);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkerQuestionsAdapter.PreviousQuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_question_item,null,false);
        WorkerQuestionsAdapter.PreviousQuestionItemViewHolder viewHolder = new WorkerQuestionsAdapter.PreviousQuestionItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerQuestionsAdapter.PreviousQuestionItemViewHolder holder, int position) {
        holder.textViewPhone.setText(questions.get(position).getPhone());
        holder.textViewQuestion.setText(questions.get(position).getQuestion());
        Picasso.get().load(questions.get(position).getImageUri()).into(holder.imageViewForQuestion);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobTitle = questions.get(position).getJobTitle();
                phoneClient = holder.textViewPhone.getText().toString();
                String clientQuestion = holder.textViewQuestion.getText().toString();
                Toast.makeText(context, clientQuestion, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, phoneClient, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, phoneWorker, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, jobTitle, Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(new Intent(v.getContext(), ReplayQuestionActivity.class)
                        .putExtra("phoneClient",phoneClient)
                        .putExtra("jobTitle",jobTitle)
                        .putExtra("phoneWorker",phoneWorker)
                        .putExtra("clientQuestion",clientQuestion)
                        .putExtra(EXTR_USER_NAME,workerName));
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
