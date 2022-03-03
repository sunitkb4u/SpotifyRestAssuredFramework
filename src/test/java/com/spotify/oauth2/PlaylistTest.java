package com.spotify.oauth2;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTest {


    @Test
    public void shouldBe_able_to_create_playlist_test(){
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New Playlist description").
                setPublic(false);
        Response response=PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(),equalTo(201));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(requestPlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(requestPlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(requestPlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));

    }

    @Test
    public void shouldBe_able_Get_playlist_test(){

        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New Playlist description").
                setPublic(false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertThat(response.statusCode(),equalTo(200));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(requestPlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(requestPlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(requestPlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));

    }

    @Test
    public void shouldBe_able_to_update_playlist_test(){
        Playlist requestPlaylist = new Playlist().
                setName("updated Playlist").
                setDescription("New updated Playlist description").
                setPublic(false);

        Response response = PlaylistApi.update(DataLoader.getInstance().updatePlaylistId(), requestPlaylist);
        assertThat(response.statusCode(),equalTo(200));
    }

    //Negative test case without Playlist name
    @Test
    public void shouldBe_not_be_able_to_create_playlist_test(){
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("New Playlist description").
                setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(),equalTo(400));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(),equalTo(400));
        assertThat(error.getError().getMessage(),equalTo("Missing required field: name"));
    }

    //Negative test case with expired token
    @Test
    public void shouldBe_not_be_able_to_create_playlist_with_expired_token(){
        String invalid_token = "1234";
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New Playlist description").
                setPublic(false);

        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertThat(response.statusCode(),equalTo(401));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(),equalTo(401));
        assertThat(error.getError().getMessage(),equalTo("Invalid access token"));
    }
}
