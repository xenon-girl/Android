package com.example.orderapp.ui.itemDetails;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.orderapp.Adapter.MyStuffListAdapter;
import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.CommentModel;
import com.example.orderapp.Model.StuffModel;
import com.example.orderapp.R;
import com.example.orderapp.ui.comments.CommentFragment;
import com.example.orderapp.ui.stuffList.StuffListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ItemDetailFragment extends Fragment {


    private ItemDetailViewModel itemDetailViewModel;
    private Unbinder unbinder;
    private android.app.AlertDialog waitingDialog;

    @BindView(R.id.img_item)
    ImageView img_item;
    @BindView(R.id.btnCart)
    CounterFab btnCart;
    @BindView(R.id.btnRating)
    FloatingActionButton btnRating;
    @BindView(R.id.stuff_name)
    TextView stuff_name;
    @BindView(R.id.item_description)
    TextView item_description;
    @BindView(R.id.item_price)
    TextView item_price;
    @BindView(R.id.number_button)
    ElegantNumberButton number_button;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.btnShowComment)
    Button btnShowComment;

    @OnClick(R.id.btnRating)
    void OnRatingButtonClick(){
        showDialogRating();
    }

    @OnClick(R.id.btnShowComment)
    void onShowCommentButtonClick(){
        CommentFragment commentFragment = CommentFragment.getInstance();
        commentFragment.show(getActivity().getSupportFragmentManager(), "CommentFragment");

    }

    private void showDialogRating() {

//        android.app.AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Rate the Item");
//        builder.setMessage("Please fill information");
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_rating,null);
        RatingBar ratingBar = (RatingBar)itemView.findViewById(R.id.rating_bar);
        EditText edt_comment = (EditText) itemView.findViewById(R.id.edit_comment);
        builder.setView(itemView);
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton("Ok", (dialog, which) -> {

            CommentModel commentModel = new CommentModel();
            commentModel.setName(Common.current_User.getName());
            commentModel.setComment(edt_comment.getText().toString());
            commentModel.setRatingValue(ratingBar.getRating());
            Map<String, Object> serverTimeStamp = new HashMap<>();
            serverTimeStamp.put("timeStamp", ServerValue.TIMESTAMP);
            commentModel.setCommentTimeStamp(serverTimeStamp);

            itemDetailViewModel.setCommentModel(commentModel);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemDetailViewModel =
                ViewModelProviders.of(this).get(ItemDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stuff_detail, container, false);

        unbinder = ButterKnife.bind(this, root);
        initViews();

        itemDetailViewModel.getStuffModelMutableLiveData().observe(getViewLifecycleOwner(), stuffModel -> {
            displayInfo(stuffModel);
        });

        itemDetailViewModel.getCommentModelMutableLiveData().observe(getViewLifecycleOwner(), commentModel -> {
            submitRatingToFirebase(commentModel);
        });
        return root;
    }

    private void initViews() {
        waitingDialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();
    }

    private void submitRatingToFirebase(CommentModel commentModel) {
        waitingDialog.show();
        //First we will submit to Comments Ref
        FirebaseDatabase.getInstance()
                .getReference(Common.COMMENT_REF)
                .child(Common.selectedStuff.getId())
                .push()
                .setValue(commentModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        //After submit to CommentRef, we will update value avgeR in Item
                        addRatingToItem(commentModel.getRatingValue());

                    }
                    waitingDialog.dismiss();
                });
    }

    private void addRatingToItem(float ratingValue) {

        FirebaseDatabase.getInstance()
                .getReference(Common.CATEGORY_REF)
                .child(Common.categorySelected.getItem_id()) //select category
                .child("items") //select array list "items" of this category
                .child(Common.selectedStuff.getKey()) //Because stuff item is array list so key is index of arrayList
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            StuffModel stuffModel = dataSnapshot.getValue(StuffModel.class);
                            stuffModel.setKey(Common.selectedStuff.getKey());

                            //Apply Rating
                            if (stuffModel.getRatingValue() == null){
                                stuffModel.setRatingValue(0d);
                            }
                            if (stuffModel.getRatingCount() == null){
                                stuffModel.setRatingCount(0l);
                            }
                            double sumRating = stuffModel.getRatingValue()+ratingValue;
                            long ratingCount = stuffModel.getRatingCount()+1;
                            double result = sumRating/ratingCount;

                            Map<String, Object> updateData = new HashMap<>();
                            updateData.put("ratingValue", result);
                            updateData.put("ratingCount", ratingCount);

                            //Update data in variable
                            stuffModel.setRatingValue(result);
                            stuffModel.setRatingCount(ratingCount);

                            dataSnapshot.getRef()
                                    .updateChildren(updateData)
                                    .addOnCompleteListener(task -> {
                                        waitingDialog.dismiss();
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                                            Common.selectedStuff = stuffModel;
                                            itemDetailViewModel.setStuffModel(stuffModel); //Call Refresh
                                        }
                                    });
                        }
                        else {
                            waitingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        waitingDialog.dismiss();
                        Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayInfo(StuffModel stuffModel) {
        Glide.with(getContext()).load(stuffModel.getImage()).into(img_item);
        stuff_name.setText(new StringBuilder(stuffModel.getName()));
        item_description.setText(new StringBuilder(stuffModel.getDescription()));
        item_price.setText(new StringBuilder(stuffModel.getPrice().toString()));

        if(stuffModel.getRatingValue() != null){
            ratingBar.setRating(stuffModel.getRatingValue().floatValue());
        }


        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(Common.selectedStuff.getName());

    }
}
