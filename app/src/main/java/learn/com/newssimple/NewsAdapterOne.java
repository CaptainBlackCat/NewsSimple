package learn.com.newssimple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import learn.com.newssimple.Utils.ImageLoaderUtils;
import learn.com.newssimple.beans.NewsBean;
import learn.com.newssimple.beans.NewsBeanRealm;
import learn.com.newssimple.main.view.BaseActivity;
import learn.com.newssimple.news.widget.BannerView;

/**
 * Created by zyx on 2017/6/11.
 */

public class NewsAdapterOne extends RecyclerView.Adapter {
    private List<NewsBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_FOOTER = 2;
    private OnItemClickListener mOnItemClickListener;
    private List<NewsBeanRealm> BannerData = new ArrayList<>();

    public NewsAdapterOne(Context mContext) {
        this.mContext = mContext;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new ItemViewHolder(v);
        }
          else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
          //因为banner占据1一个位置，所以...
            NewsBean news = mData.get(position);
            if(news == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
//            Uri uri = Uri.parse(news.getImgsrc());
//            ((ItemViewHolder) holder).mNewsImg.setImageURI(uri);
            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg, news.getImgsrc());
        }

    }
    public void setmData(List<NewsBean> data){
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if (mData==null){
            return begin;
        }
        return mData.size()+begin;
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter) {
            return TYPE_ITEM;
        }

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }




    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
         if (mOnItemClickListener!=null){
             mOnItemClickListener.onItemClick(view, this.getPosition());
         }
        }
    }


    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public NewsBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
