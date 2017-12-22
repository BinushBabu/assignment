package in.museon.assignment.data.rest;
import in.museon.assignment.data.rest.entity.response.Response_Profile;
import in.museon.assignment.data.rest.entity.response.Response_Reg;
import in.museon.assignment.data.rest.entity.response.Response_Upload;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiClient {
    @Multipart
    @POST("reg")
    Call<Response_Reg> regUser(@Part MultipartBody.Part image,  @Part("name") String name,
                               @Part("mobile") String mobile);

    @GET("profile")
    Call<Response_Profile>  getAllProfileList();

    @Multipart
    @POST("upload")
    Call<Response_Upload> uploadImage(@Part MultipartBody.Part image,
                                      @Part("mobile") String mobile);
}
