package de.mateco.integrAMobile.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.CustomerActivityModel;
import de.mateco.integrAMobile.model.DailyAgendaModel;

/**
 * Created by mmehta on 26.04.2016.
 */
public class CustomerActivityComparable implements Comparator<CustomerActivityModel>
{
    private int sortingVia = 0;
    private int type = 0;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public CustomerActivityComparable(int sortingVia, int type)
    {
        this.sortingVia = sortingVia;
        this.type = type;
    }

    @Override
    public int compare(CustomerActivityModel sourceModel, CustomerActivityModel compareWithModel)
    {
        switch (sortingVia)
        {
            case 1:
                if(type == 0)
                {
                    return sortDate(sourceModel, compareWithModel);
                }
                else
                {
                    return sortDate(compareWithModel, sourceModel);
                }
            case 2:
                if(type == 0)
                {
                    return sortDesignation(sourceModel, compareWithModel);
                }
                else
                {
                    return sortDesignation(compareWithModel, sourceModel);
                }
            case 3:
                if(type == 0)
                {
                    return sortRealized(sourceModel, compareWithModel);
                }
                else
                {
                    return sortRealized(compareWithModel, sourceModel);
                }
        }
        return 0;
    }

        private int sortDesignation(CustomerActivityModel sourceModel, CustomerActivityModel compareWithModel)
    {
        return sourceModel.getAktivitatstyp().compareToIgnoreCase(compareWithModel.getAktivitatstyp());
    }

    private int sortRealized(CustomerActivityModel sourceModel, CustomerActivityModel compareWithModel)
    {
        return sourceModel.getRealisiert().compareToIgnoreCase(compareWithModel.getRealisiert());
    }

    private int sortDate(CustomerActivityModel sourceModel, CustomerActivityModel compareWithModel)
    {
        try {
            return format.parse(sourceModel.getStartdatum()).after(format.parse(compareWithModel.getStartdatum())) ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
