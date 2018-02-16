package de.mateco.integrAMobile.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import de.mateco.integrAMobile.model.ProjectDetailActivityModel;

/**
 * Created by mmehta on 26.04.2016.
 */
public class ProjectActivityComparable implements Comparator<ProjectDetailActivityModel>
{
    private int sortingVia = 0;
    private int type = 0;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public ProjectActivityComparable(int sortingVia, int type)
    {
        this.sortingVia = sortingVia;
        this.type = type;
    }
    private int sortDesignation(ProjectDetailActivityModel sourceModel, ProjectDetailActivityModel compareWithModel)
    {
        return sourceModel.getAktivitatstyp().compareToIgnoreCase(compareWithModel.getAktivitatstyp());
    }

    private int sortRealized(ProjectDetailActivityModel sourceModel, ProjectDetailActivityModel compareWithModel)
    {
        return sourceModel.getRealisiert().compareToIgnoreCase(compareWithModel.getRealisiert());
    }

    private int sortDate(ProjectDetailActivityModel sourceModel, ProjectDetailActivityModel compareWithModel)
    {
        try {
            return format.parse(sourceModel.getStartdatum()).after(format.parse(compareWithModel.getStartdatum())) ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int compare(ProjectDetailActivityModel sourceModel, ProjectDetailActivityModel compareWithModel) {
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
}
