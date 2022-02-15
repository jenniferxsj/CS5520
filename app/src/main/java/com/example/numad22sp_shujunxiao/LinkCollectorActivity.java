package com.example.numad22sp_shujunxiao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {
    private ArrayList<ItemCard> itemList = new ArrayList<ItemCard>();
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private AlertDialog dialog;
    private ConstraintLayout layout;
    private TextView emptyText;
    private TextView enterItem;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        buildDialog();
        init(savedInstanceState);

        emptyText = findViewById(R.id.text_empty);
        enterItem = findViewById(R.id.text_add_item);

        layout = findViewById(R.id.activity_link);
        addButton = findViewById(R.id.floatingActionButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //don't allow change position
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                showSnackBar("Deleted successful");
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LinkCollectorActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        builder.setView(view);
        builder.setTitle("Enter Link")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputLayout inputName = view.findViewById(R.id.input_name);
                        TextInputLayout inputLink = view.findViewById(R.id.input_link);
                        String linkText = inputLink.getEditText().getText().toString().trim();
                        if(URLUtil.isValidUrl(linkText) && Patterns.WEB_URL.matcher(linkText).matches()) {
                            addCard(inputName.getEditText().getText().toString().trim(), linkText);
                            showSnackBar("Added successful");
                        } else {
                            showSnackBar("Enter url is invalid, please re-enter");
                        }
                        inputName.getEditText().setText("");
                        inputLink.getEditText().setText("");
                    }
        });
        dialog = builder.create();
    }

    private void addCard(String nameText, String linkText) {
        try {
            itemList.add(0, new ItemCard(R.drawable.ic_link_foreground, nameText, linkText, false));
            adapter.notifyItemInserted(0);
        } catch(Exception e) {
            throw new IllegalArgumentException("Something went wrong");
        }
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecycleView();
    }

    private void initialItemData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            emptyText = findViewById(R.id.text_empty);
            emptyText.setVisibility(View.INVISIBLE);
            enterItem = findViewById(R.id.text_add_item);
            enterItem.setVisibility(View.INVISIBLE);

            if (itemList == null || itemList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String list = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "3");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though
                    if (isChecked) {
                        name = name.substring(0, name.lastIndexOf("("));
                    }
                    ItemCard itemCard = new ItemCard(R.drawable.ic_link_foreground, name, list, isChecked);
                    itemList.add(itemCard);
                }
            }
        }
    }

    private void createRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new RViewAdapter(LinkCollectorActivity.this, itemList);

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemList.get(position).onItemClick(position);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onCheckBoxClick(int position) {
                itemList.get(position).onItemClick(position);
                adapter.notifyItemChanged(position);
            }
        };

        adapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            private void checkEmpty() {
                if(adapter.getItemCount() == 0) {
                    emptyText.setVisibility(View.VISIBLE);
                    enterItem.setVisibility(View.VISIBLE);
                } else {
                    emptyText.setVisibility(View.INVISIBLE);
                    enterItem.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }
        });
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        for (int i = 0; i < size; i++) {
            // put image information id into instance
            outState.putInt(KEY_OF_INSTANCE + i + "0", itemList.get(i).getImg());
            // put itemName information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getName());
            // put itemDesc information into instance
            outState.putString(KEY_OF_INSTANCE + i + "2", itemList.get(i).getLink());
            // put isChecked information into instance
            outState.putBoolean(KEY_OF_INSTANCE + i + "3", itemList.get(i).getStatus());
        }
        super.onSaveInstanceState(outState);
    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(layout, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();
    }
}