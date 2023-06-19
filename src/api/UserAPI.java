package api;

import java.util.List;

import entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
	
	@GET("user/findall")
	Call<List<User>> findAll();
	
	@GET("user/find/{id}")
	Call<User>find(@Path("id")String id);

	@POST("user/create")
	Call<Void> create(@Body User user);
	
	@PUT("user/update")
	Call<Void> update(@Body User user);
	
	@DELETE("user/delete/{id}")
	Call<Void>delete(@Path("id")String id);
}
