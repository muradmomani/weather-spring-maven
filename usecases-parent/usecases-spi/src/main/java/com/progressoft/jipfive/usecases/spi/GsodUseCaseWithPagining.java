package com.progressoft.jipfive.usecases.spi;

import java.util.List;

import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;

public interface GsodUseCaseWithPagining extends GsodUseCase{
	List<GsodSummery> getSummeryByCtryDateAndPaging(GsodParamReader gsodReader);

}
