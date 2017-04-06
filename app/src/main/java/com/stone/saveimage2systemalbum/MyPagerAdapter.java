package com.stone.saveimage2systemalbum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author stone
 * @date 17/3/24
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    String urls[] = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490332674188&di=d4862bd1946257ec7e41a300a7d7eb61&imgtype=0&src=http%3A%2F%2Ffa.topitme.com%2Fa%2F14%2F82%2F1127885501a7f8214ao.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490322649&di=f5e4f27e7309a597b54e39201db6502b&src=http://img1.cache.netease.com/catchpic/2/2F/2FADF24B970733999318F052D021D206.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490322649&di=895f3ef65a1582ac48061c6719cfe8c0&src=http://www.bylife.net/zb_users/upload/2014/1/2014012984017801.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490322684&di=59e25104fe3a545507163cec9e1a4a9e&src=http://p3.gexing.com/G1/M00/95/32/rBACJlWh-meSgUanAAHkZwmyVgc187.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490324284&di=9eca536ea19bac447fbbb07d992cc4c6&src=http://f1.bj.anqu.com/down/Nzk5NQ==/allimg/1212/60-12122QP624.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490324311&di=8ac8a460eadd11a0e32348f630f8cf47&src=http://img1.cache.netease.com/catchpic/3/3A/3A49CC7ADF5E13A20FA305BE06E79B69.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490334418517&di=f399bc022f84edc3bee4dd4cec085af2&imgtype=0&src=http%3A%2F%2Ffile.qqzzhh.com%2Fupload%2Fpic%2F201311%2F24%2F01%2F29%2F521f4b7534340820.jpg%2521600x600.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490324355&di=2ed8efa6d278ca22bbfc017a5a8d9518&src=http://www.sznews.com/humor/attachement/gif/site3/20121214/4487fcd7fc661234c31c25.gif",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490332674188&di=d4862bd1946257ec7e41a300a7d7eb61&imgtype=0&src=http%3A%2F%2Ffa.topitme.com%2Fa%2F14%2F82%2F1127885501a7f8214ao.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3748748165,1814616012&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490334486022&di=afe4b77a64e8b0f593c791ec5731cb5d&imgtype=0&src=http%3A%2F%2Fimages.3158.cn%2Fdata%2Fattachment%2Fshanghai%2Farticle%2F2014%2F10%2F24%2Fa9a756f02cf7b4fba82239636889f79c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490334510365&di=2006d335354f91eeb049df4ecfcfed90&imgtype=0&src=http%3A%2F%2Frescdn.qqmail.com%2Fdyimg%2F20131108%2F7A1C476F9196.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490334530300&di=2cff9e11a5a7e35b452c4fb4ee819656&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F12%2F60%2F80X58PICUgn_1024.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490334547086&di=9c4f31ef663413bc7e6f99e92e8dc9a9&imgtype=0&src=http%3A%2F%2Fp4.gexing.com%2FG1%2FM00%2F95%2F32%2FrBACJlWh-meBqtEFAAQ_jHh9W7c997.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490336602397&di=301c6e882066d57c608e8df0893bc60e&imgtype=0&src=http%3A%2F%2Fimg.article.pchome.net%2F01%2F58%2F91%2F36%2Fpic_lib%2Fwm%2F2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490326650&di=47ef7f2e78668e5c7069eef8f2796a1e&src=http://pic11.nipic.com/20101130/2635532_091529013878_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1490326669&di=d019a81d0883ab596c34cf27de779bc2&src=http://res.img.ifeng.com/e1da820fcfe53346/2011/0405/rdn_4d9a608e0ff80.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=179789277,788050198&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490931510&di=218c5aa1d7776013d0a8a61c5a194eb5&imgtype=jpg&er=1&src=http%3A%2F%2Ff1.bj.anqu.com%2Fdown%2FYzkyMw%3D%3D%2Fallimg%2F1208%2F48-120P2145116.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490336866069&di=886624679403dad71ffe1dbdbf2189aa&imgtype=0&src=http%3A%2F%2Fimgs.focus.cn%2Fupload%2Fnews%2F17450%2Fb_174496680.jpg",
    };

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(urls[position]);
    }

    @Override
    public int getCount() {
        return urls.length;
    }
}
