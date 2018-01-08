package de.mateco.integrAMobile.Helper;

import java.util.Comparator;

import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.DailyAgendaModel;

/**
 * Created by mmehta on 26.04.2016.
 */
public class ContactPersonComparable implements Comparator<ContactPersonModel>
{
    private int sortingVia = 0;
    private int type = 0;

    public ContactPersonComparable(int sortingVia, int type)
    {
        this.sortingVia = sortingVia;
        this.type = type;
    }

    @Override
    public int compare(ContactPersonModel sourceModel, ContactPersonModel compareWithModel)
    {
        switch (sortingVia)
        {
            case 1:
                if(type == 0)
                {
                    return sortSalutation(sourceModel, compareWithModel);
                }
                else
                {
                    return sortSalutation(compareWithModel, sourceModel);
                }
            case 2:
                if(type == 0)
                {
                    return sortFirstName(sourceModel, compareWithModel);
                }
                else
                {
                    return sortFirstName(compareWithModel, sourceModel);
                }
            case 3:
                if(type == 0)
                {
                    return sortFunction(sourceModel, compareWithModel);
                }
                else
                {
                    return sortFunction(compareWithModel, sourceModel);
                }
        }
        return 0;
    }

    private int sortSalutation(ContactPersonModel sourceModel, ContactPersonModel compareWithModel)
    {
        return sourceModel.getAnrede().compareToIgnoreCase(compareWithModel.getAnrede());
    }

    private int sortFirstName(ContactPersonModel sourceModel, ContactPersonModel compareWithModel)
    {
        return sourceModel.getNachname().compareToIgnoreCase(compareWithModel.getNachname());
    }

    private int sortFunction(ContactPersonModel sourceModel, ContactPersonModel compareWithModel)
    {
        return sourceModel.getFunktion().compareToIgnoreCase(compareWithModel.getFunktion());
    }
}
