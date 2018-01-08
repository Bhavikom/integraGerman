package de.mateco.integrAMobile.Helper;

import java.util.Comparator;

import de.mateco.integrAMobile.model.ContactPersonModel;
import de.mateco.integrAMobile.model.ProjectDetailTradeModel;

/**
 * Created by mmehta on 26.04.2016.
 */
public class ProjectTradeComparable implements Comparator<ProjectDetailTradeModel>
{
    private int sortingVia = 0;
    private int type = 0;

    public ProjectTradeComparable(int sortingVia, int type)
    {
        this.sortingVia = sortingVia;
        this.type = type;
    }

    @Override
    public int compare(ProjectDetailTradeModel sourceModel, ProjectDetailTradeModel compareWithModel)
    {
        switch (sortingVia)
        {
            case 1:
                if(type == 0)
                {
                    return sortMatchCode(sourceModel, compareWithModel);
                }
                else
                {
                    return sortMatchCode(compareWithModel, sourceModel);
                }
            case 2:
                if(type == 0)
                {
                    return sortGewerke(sourceModel, compareWithModel);
                }
                else
                {
                    return sortGewerke(compareWithModel, sourceModel);
                }
        }
        return 0;
    }

    private int sortMatchCode(ProjectDetailTradeModel sourceModel, ProjectDetailTradeModel compareWithModel)
    {
        return sourceModel.getMatchCode().compareToIgnoreCase(compareWithModel.getMatchCode());
    }

    private int sortGewerke(ProjectDetailTradeModel sourceModel, ProjectDetailTradeModel compareWithModel)
    {
        return sourceModel.getGewerk().compareToIgnoreCase(compareWithModel.getGewerk());
    }
}
