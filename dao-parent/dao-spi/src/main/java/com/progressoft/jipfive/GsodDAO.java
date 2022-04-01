package com.progressoft.jipfive;

import java.util.Collection;

public interface GsodDAO {

    Collection<String> getAvilableDates();
    Collection<Gsod> getSummeryByIDAndDate(ID id, String fileName);

}
