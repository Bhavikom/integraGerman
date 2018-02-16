package de.mateco.integrAMobile.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import de.mateco.integrAMobile.model_logonsquare.FutureItem;

/**
 * Created by mmehta on 30.03.2016.
 */
public class FutureAgendaComparable implements Comparator<FutureItem>
{
    //1 for kunde
    //2 for date
    // 3 for Activity
    private int sortingVia = 0;
    private int type = 0;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public FutureAgendaComparable(int sortingVia, int type)
    {
        this.sortingVia = sortingVia;
        this.type = type;
    }

    @Override
    public int compare(FutureItem sourceModel, FutureItem compareWithModel)
    {
        switch (sortingVia)
        {
            case 1:
                if(type == 0)
                {
                    return sortKundeVise(sourceModel, compareWithModel);
                }
                else
                {
                    return sortKundeVise(compareWithModel, sourceModel);
                }
            case 2:
                if(type == 0)
                {
                    return sortDateVise(sourceModel, compareWithModel);
                }
                else
                {
                    return sortDateVise(compareWithModel, sourceModel);
                }
            case 3:
                if(type == 0)
                {
                    return sortActivityVise(sourceModel, compareWithModel);
                }
                else
                {
                    return sortActivityVise(compareWithModel, sourceModel);
                }
        }
        return 0;
    }

    private int sortKundeVise(FutureItem sourceModel, FutureItem compareWithModel)
    {
        return sourceModel.getName1().compareToIgnoreCase(compareWithModel.getName1());
    }

    private int sortActivityVise(FutureItem sourceModel, FutureItem compareWithModel)
    {
        return sourceModel.getAktivitaetstytp().compareToIgnoreCase(compareWithModel.getAktivitaetstytp());
    }

    private int sortDateVise(FutureItem sourceModel, FutureItem compareWithModel)
    {
        try {
            /* change on 14th June 2017 to solve crash */
            //return format.parse(sourceModel.getDatum()).after(format.parse(compareWithModel.getDatum())) ? 1 : -1;
            if(format.parse(sourceModel.getDatum()).after(format.parse(compareWithModel.getDatum()))){
                return 1;
            }else if(format.parse(sourceModel.getDatum()).before(format.parse(compareWithModel.getDatum()))){
                return -1;
            }else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}