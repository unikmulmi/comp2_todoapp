package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.List;


public class TodoListFragment extends Fragment {
    View rootView;
    private TodoViewModel mTodoViewModel;
    RecyclerView todoRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoRecyclerView = rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoRecyclerView.setLayoutManager(layoutManager);
        updateRV();

        return rootView;
    }

    //for displaying todo list in main page
    void updateRV(){
        mTodoViewModel.getAllTodos().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> todoList) {
                TodoAdapter adapter = new TodoAdapter(todoList);
                todoRecyclerView.setAdapter(adapter);
            }
        });
    }
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{
        List<Task> mTodoList;
        public TodoAdapter(List<Task> todoList){
            mTodoList = todoList;
        }
        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new TodoHolder(layoutInflater, parent);
        }
        //for changing the color of todo list according to the priority
        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            Task todo = mTodoList.get(position);
            LinearLayout layout = (LinearLayout)((ViewGroup)holder.mTitle.getParent());
            switch (todo.getPriority()){
                case 1:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_high_priority));
                    break;
                case 2:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_medium_priority));
                    break;
                case 3:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_low_priority));
                    break;
            }
            holder.bind(todo);
        }
        @Override
        public int getItemCount() {
            return mTodoList.size();
        }
        public Task getTodo(int index){
            return mTodoList.get(index);
        }

        public Task getTodoAt(int index){
            return mTodoList.get(index);
        }
    }
    private class TodoHolder extends RecyclerView.ViewHolder{
        TextView mTitle, mDate, mDesprition;
        public TodoHolder(LayoutInflater inflater, ViewGroup parentViewGroup) {
            super(inflater.inflate(R.layout.list_item_todo, parentViewGroup, false));
            mTitle = itemView.findViewById(R.id.list_title);
            mDate = itemView.findViewById(R.id.list_date);
            mDesprition=itemView.findViewById(R.id.list_description);

            //for updating the list while the user clicks the todo
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(),EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
            mDesprition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(),EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
            mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adapter = new TodoAdapter(mTodoViewModel.getAllTodos().getValue());
                    int position = getAdapterPosition();
                    Task task = adapter.getTodoAt(position);
                    Intent intent = new Intent(getActivity(),EditTodoActivity.class);
                    intent.putExtra("TodoId", task.getId());
                    startActivity(intent);
                }
            });
        }
        //For displaying the list
        public void bind(Task todo){
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            mTitle.setText(todo.getTitle());
            mDesprition.setText(todo.getDescription());
            mDate.setText(dateFormatter.format(todo.getCreatedDate()));
        }

    }
}