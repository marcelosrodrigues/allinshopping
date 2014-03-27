package com.pmrodrigues.android.allinshopping.integration;

import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.integration.downloads.Download;
import com.pmrodrigues.android.allinshopping.integration.upload.Upload;

public interface Integration
{

	@SuppressWarnings("rawtypes")
	Download getDownload(ResourceType resourcetype);

	Upload getUpload(ResourceType resourcetype);
}
