package com.example.fixawy.Client.ReplyQuestions;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fixawy.Pojos.Answer;
import com.example.fixawy.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.PreviousQuestionItemViewHolder> {

    ArrayList<Answer> answers = new ArrayList<>();
    Context context;
    String jobTitle;
    String phoneClient,clientName;

    public void clear() {
        answers.clear();
    }

    public void add(Answer ans){
        answers.add(ans);
        notifyDataSetChanged();
    }

    public AnswerAdapter(Context context, String jobTitle,String phoneClient,String clientName) {
        this.context = context;
        this.jobTitle = jobTitle;
        this.phoneClient = phoneClient;
        this.clientName = clientName;
    }


    @NonNull
    @Override
    public AnswerAdapter.PreviousQuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,null,false);
        AnswerAdapter.PreviousQuestionItemViewHolder viewHolder = new AnswerAdapter.PreviousQuestionItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.PreviousQuestionItemViewHolder holder, int position) {
        holder.textViewAnswers.setText(answers.get(position).getReplay());
        holder.textViewPhone.setText(answers.get(position).getPhone());
        holder.textViewQuestion.setText(answers.get(position).getClientQuestion());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneWorker = answers.get(position).getPhone();
                Toast.makeText(v.getContext(),phoneWorker, Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), jobTitle, Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), phoneClient, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class PreviousQuestionItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAnswers,textViewPhone,textViewQuestion;
        public View layout;
        public PreviousQuestionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            textViewAnswers = itemView.findViewById(R.id.answer);
            textViewPhone = itemView.findViewById(R.id.phone);
            textViewQuestion = itemView.findViewById(R.id.question);
        }
    }
}

