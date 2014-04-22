package com.pmrodrigues.android.allinshopping.integration;

import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.integration.downloads.Download;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCEP;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadCliente;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadEstado;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadProdutos;
import com.pmrodrigues.android.allinshopping.integration.downloads.DownloadSecoes;
import com.pmrodrigues.android.allinshopping.integration.upload.Upload;
import com.pmrodrigues.android.allinshopping.integration.upload.UploadCliente;
import com.pmrodrigues.android.allinshopping.integration.upload.UploadPedido;

class PrestashopIntegration
    implements Integration
{

     @Override
	public Download getDownload(ResourceType resourcetype)
    {
        
        Object obj = null;
        if (resourcetype == ResourceType.CEP){
            obj = new DownloadCEP();
        } else if (resourcetype == ResourceType.SECOES) {
           obj = new DownloadSecoes();
        } else if (resourcetype == ResourceType.CLIENTE) {
           obj = new DownloadCliente();
        } else if (resourcetype == ResourceType.PRODUTOS){
           obj = new DownloadProdutos();
        } else if( resourcetype == ResourceType.ESTADOS ) {
        	obj = new DownloadEstado();
        }
        
        return ((Download) (obj));
    }

    @Override
	public Upload getUpload(ResourceType resourcetype)
    {   
        Object obj = null;
        if (resourcetype == ResourceType.CLIENTE){  
        	obj = new UploadCliente();
        } else if (resourcetype == ResourceType.PEDIDO) {
            obj = new UploadPedido();
        } 
        return ((Upload) (obj));
    }
}
