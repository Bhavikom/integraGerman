package de.mateco.integrAMobile.model_logonsquare;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class ResponseMainAgenda {

	@SerializedName("Past")
	@JsonField(name ="Past")
	private List<PastItem> past;

	@SerializedName("Future")
	@JsonField(name ="Future")
	private List<FutureItem> future;

	@SerializedName("Termine")
	@JsonField(name ="Termine")
	private List<TermineItem> termine;

	@SerializedName("Telefonate")
	@JsonField(name ="Telefonate")
	private List<TelefonateItem> telefonate;

	public void setPast(List<PastItem> past){
		this.past = past;
	}

	public List<PastItem> getPast(){
		return past;
	}

	public void setFuture(List<FutureItem> future){
		this.future = future;
	}

	public List<FutureItem> getFuture(){
		return future;
	}

	public void setTermine(List<TermineItem> termine){
		this.termine = termine;
	}

	public List<TermineItem> getTermine(){
		return termine;
	}

	public void setTelefonate(List<TelefonateItem> telefonate){
		this.telefonate = telefonate;
	}

	public List<TelefonateItem> getTelefonate(){
		return telefonate;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMainAgenda{" +
			"past = '" + past + '\'' + 
			",future = '" + future + '\'' + 
			",termine = '" + termine + '\'' + 
			",telefonate = '" + telefonate + '\'' + 
			"}";
		}
}