package com.craps.myapplication.Utils;



    public class TMDBHelper {

        private static String apiKey = "58258ed803227d3e65563dd938fa2677";
        public static final String DEVELOPER_KEY="AIzaSyB5mPI-JcBDcyZlu40yG1dkPUq2V7ydqSo";
        private static String baseUrl = "https://api.themoviedb.org/3";
        private static String imgBaseUrl="http://image.tmdb.org/t/p/";

        public static final String language_ENGLISH = "en-US";
        public static final String language_SPANISH = "es-ES";



        public static final String IMAGE_SIZE_W45 = "w45";
        public static final String IMAGE_SIZE_W92= "w92";
        public static final String IMAGE_SIZE_W154 = "w154";
        public static final String IMAGE_SIZE_W185 = "w185";
        public static final String IMAGE_SIZE_W300 = "w300";
        public static final String IMAGE_SIZE_W342 = "w342";
        public static final String IMAGE_SIZE_W780 = "w780";
        public static final String IMAGE_SIZE_W1280 = "w1280";
        public static final String IMAGE_SIZE_ORIGINAL = "original";
        public static final String IMAGE_SIZE_H632 = "h632";

        public static final String MOVIE_GENRE_ACTION = "28";
        public static final String MOVIE_GENRE_ACCION = "28";
        public static final String MOVIE_GENRE_ADVENTURE = "12";
        public static final String MOVIE_GENRE_AVENTURA = "12";
        public static final String MOVIE_GENRE_ANIMATION = "16";
        public static final String MOVIE_GENRE_ANIMACION = "16";
        public static final String MOVIE_GENRE_COMEDY = "35";
        public static final String MOVIE_GENRE_COMEDIA = "35";
        public static final String MOVIE_GENRE_CRIME = "80";
        public static final String MOVIE_GENRE_CRIMEN = "80";
        public static final String MOVIE_GENRE_DOCUMENTARY = "99";
        public static final String MOVIE_GENRE_DOCUMENTAL = "99";

        public static final String MOVIE_GENRE_DRAMA = "18";
        public static final String MOVIE_GENRE_FAMILY = "10751";
        public static final String MOVIE_GENRE_FAMILIA = "10751";
        public static final String MOVIE_GENRE_FANTASY = "14";
        public static final String MOVIE_GENRE_FANTASIA = "14";
        public static final String MOVIE_GENRE_HISTORY = "36";
        public static final String MOVIE_GENRE_HISTORIA= "36";

        public static final String MOVIE_GENRE_HORROR = "27";
        public static final String MOVIE_GENRE_MUSIC = "10402";
        public static final String MOVIE_GENRE_MUSICA = "10402";
        public static final String MOVIE_GENRE_MYSTERY = "9648";
        public static final String MOVIE_GENRE_MYSTERIO = "9648";

        public static final String MOVIE_GENRE_ROMANCE = "10749";
        public static final String MOVIE_GENRE_SCIENCE_FICTION = "878";
        public static final String MOVIE_GENRE_SCIENCIA_FICCION = "878";
        public static final String MOVIE_GENRE_SCIENCE_TV_MOVIE = "10770";
        public static final String MOVIE_GENRE_SCIENCE_THRILLER = "53";

        public static final String MOVIE_GENRE_SCIENCE_WAR = "10752";

        public static final String MOVIE_GENRE_SCIENCE_WESTERN = "37";

        public static final String TV_GENRE_ACTION_AND_ADVENTURE = "10759";
        public static final String TV_GENRE_ANIMATION = "16";
        public static final String TV_GENRE_COMEDY = "35";
        public static final String TV_GENRE_CRIME = "80";
        public static final String TV_GENRE_DOCUMENTARY = "99";
        public static final String TV_GENRE_DRAMA = "18";
        public static final String TV_GENRE_FAMILY = "10751";
        public static final String TV_GENRE_KIDS = "10762";
        public static final String TV_GENRE_MYSTERY = "9648";
        public static final String TV_GENRE_NEWS = "10763";
        public static final String TV_GENRE_REALITY = "10764";
        public static final String TV_GENRE_SCI_FI_AND_FANTASY = "10765";


        public static final String TV_GENRE_FANTASY = "14";
        public static final String TV_GENRE_HISTORY = "36";
        public static final String TV_GENRE_HORROR = "27";
        public static final String TV_GENRE_MUSIC = "10402";
        public static final String TV_GENRE_ROMANCE = "10749";

        public static String getImagenesAdicionales(Integer personId){
            return  baseUrl+"/person/"+personId+"/images?api_key="+apiKey;
        }

        public static String getCastMovie(Integer movieId,String language){
            return baseUrl + "/movie/" + movieId + "/credits?api_key=" + apiKey + "&language=" + language;
        }

        public static String getCastTv(Integer tvId, String language){
            return baseUrl +"/tv/" + tvId + "/credits?api_key=" + apiKey + "&language=" + language;
        }

        public static String getPerson(Integer personId, String language){
            return baseUrl +"/person/" + personId +"?api_key=" + apiKey +"&language="+language;
        }

        public static String irATmdb(Integer idFormato,String tipoFormato){
            if (tipoFormato.equals("series")){
                return "https://www.themoviedb.org/tv/"+idFormato;
            }
            else{
                return "https://www.themoviedb.org/movie/"+idFormato;
            }
        }





        //los de nivo
        public static String getMovieDetailURL(Integer movieID,String language){
            return baseUrl + "/movie/" + movieID + "?api_key=" + apiKey +"&language="+language;
        }






        public static String getTrailerURL(Integer movideID,String language){
            return baseUrl + "/movie/" + movideID + "/videos?api_key=" +apiKey +"&language="+language;
        }

        public static String getMoviesRecomended(Integer movideID,String language, Integer page){
            return baseUrl + "/movie/" + movideID + "/recomendations?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getSimilarMovies(Integer movideID,String language, Integer page){
            return baseUrl + "/movie/" + movideID + "/similar?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getMoviesReviews(Integer movideID,String language, Integer page){
            return baseUrl + "/movie/" + movideID + "/reviews?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getMoviePlayList(Integer movideID,String language, Integer page){
            return baseUrl + "/movie/" + movideID + "/lists?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getLastestMovie(String language, Integer page){
            return baseUrl + "/movie/"+"latest?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getNowPlayingMovies(String language, Integer page){
            return baseUrl + "/movie/" + "now_playing?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getPopularMovies(String language, Integer page){
            return baseUrl + "/movie/" + "popular?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getBestMoviesOfSpecificYear(String language, String specificYear, Integer page){

            return baseUrl + "/discover/movie" + "?api_key=" + apiKey + "&primary_release_year=" + specificYear +
                    "&sort_by=vote_average.desc&language=" + language + "&page=" + page.toString();
        }

        public static String getHighestGrossingMovies(String language_ENGLISH,String specificYear,Integer page){

            return baseUrl + "/discover/movie?api_key=" + apiKey + "&sort_by=revenue.desc" + "&primary_release_year=" + specificYear +
                    "&language=" + language_ENGLISH + "&page=" + page.toString();
        }

        public static String getTopRatedMovies(String language, Integer page){
            return baseUrl + "/discover/movie/" + "?api_key=" +apiKey +"&sort_by=vote_average.desc&language="+language+"&page="+page.toString();
        }

        public static String getUpcomingMovies(String language, Integer page){
            return baseUrl + "/movie/" + "upcoming?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getImagePoster(String size, String imagePath){
            return "https://image.tmdb.org/t/p/"+size+imagePath;
        }
        public static String getBackDropPoster(String size, String imagePath){
            return "https://image.tmdb.org/t/p/"+size+imagePath;
        }

        public static String getAllGenres(String language){
            return baseUrl + "/genre/list?api_key=" +apiKey +"&language="+language;
        }

        public static String getMoviesByGenre(String genre,String language, Integer page){
            return baseUrl + "/discover/movie?api_key=" +apiKey +"&language="+language+"&sort_by=popularity.desc&include_adult=false&include_video=true&page="+page.toString()+"&with_genres="+genre;
        }




        public static String getTVByGenre(String genre,String language, Integer page){
            return baseUrl + "/discover/tv?api_key=" +apiKey +"&language="+language+"&sort_by=popularity.desc&include_adult=false&include_video=true&page="+page.toString()+"&with_genres="+genre+"&include_null_first_air_dates=false";
        }

        public static String getTVShowDetail(Integer tvShow,String language){
            return baseUrl + "/tv/"+tvShow+"?api_key=" +apiKey +"&language="+language;
        }

        public static String getTVShowRecomendedForTVShow(Integer tvShow,String language, Integer page){
            return baseUrl + "/tv/"+tvShow+"/similar?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getTVPopular(String language, Integer page){
            return baseUrl + "/tv/popular?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getTVTopRated(String language, Integer page){
            return baseUrl + "/tv/on_the_air?api_key=" +apiKey +"&language="+language+"&page="+page.toString();
        }

        public static String getTVShowVideo(String language, Integer tvShowId){
            return baseUrl + "/tv/"+tvShowId+"/videos?api_key=" +apiKey +"&language="+language;
        }

        public static String getTVSeasonDetail(String tvShowId, Integer season, String language){
            return baseUrl + "/tv/"+tvShowId+"/season/"+season.toString()+"?api_key=" +apiKey +"&language="+language;
        }

        public static String getTVEpisodeDetail(String tvShowId, Integer season, String language){
            return baseUrl + "/tv/"+tvShowId+"/season/"+season.toString()+"?api_key=" +apiKey +"&language="+language;
        }
        public static String getTVAiringToday(String language, Integer page){
            return baseUrl + "/tv/airing_today?api_key=" + apiKey + "&language="+language + "&page=" +page.toString();

        }

        public static String searchMovie(String query, String languaje, Integer page){
            return baseUrl+ "/search/movie?api_key="+ apiKey +"&language="+languaje+"&query="+query+"&page=" +page.toString();
        }

        public static String searchTv(String query, String languaje, Integer page){
            return baseUrl+ "/search/tv?api_key="+ apiKey +"&language="+languaje+"&query="+query+"&page=" +page.toString();
        }

        public static String getCredits(Integer personId, String languaje){
            return baseUrl+ "/person/"+personId+"/combined_credits?api_key="+ apiKey +"&language="+languaje;
        }


    }




