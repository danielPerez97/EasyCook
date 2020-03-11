package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.view.fragments.CookStepPagerAdapter;
import capstone.project.easycook.viewmodel.CookRecipeViewModel;
import daniel.perez.easycook.databinding.ActivityCookRecipeBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CookRecipeActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    CookRecipeViewModel viewModel;
    ActivityCookRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCookRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(CookRecipeViewModel.class);

        CookStepPagerAdapter adapter = new CookStepPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        long id = getIntent().getLongExtra("RECIPE_ID", -1);
        Disposable disposable = viewModel.steps(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setData);

        binding.exitBtn.setOnClickListener(v -> finish());
        binding.nextBtn.setOnClickListener(v -> nextItem());
    }

    public void nextItem()
    {
        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
    }

    public void lastItem()
    {
        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);
    }

    @Override
    public void onBackPressed()
    {
        if(binding.viewPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        }
        else
        {
            lastItem();
        }
    }
}
