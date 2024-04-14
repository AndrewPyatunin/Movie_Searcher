# Проект Android для получения и отображении данных о фильмах с открытого api платформы "Кинопоиск"

Этот проект демонстрирует использование Retrofit для выполнения запросов в сеть.
Room применяется для кэширования данных, а также выступает в роли Single source of truth.
Для инъекции зависимостей применяется Dagger.
Используется архитектура elm и mvvm.

## Запуск

1. Склонируйте репозиторий: bash
git clone https://github.com/AndrewPyatunin/Movie_Searcher.git

2. Для выполнения запросов в сеть нужно вставить api_key в пустой txt файл(api_key.txt), путь к его его расположению: MovieSearcher\app\src\main\assets\api_key.txt

3. Откройте проект в Android Studio и запустите на устройстве или эмуляторе.

## Примеры запросов и ответов

### Получение списка отзывов по id фильма (movieId) (GET):
Запрос:
curl -X 'GET' \
  'https://api.kinopoisk.dev/v1.4/review?page=1&limit=10&movieId=520' \
  -H 'accept: application/json' \
  -H 'X-API-KEY: {your_api_key}'

Ответ:
{
  "docs": [
    {
      "id": 3282744,
      "movieId": 520,
      "title": "",
      "type": "Позитивный",
      "review": "США; середина 70-х годов XX века\r\n\r\nГовард /Питер Финч/ - ведущий новостей на телевидении, которого собираются уволить из-за низких рейтингов, в результате чего мужчина обещает зрителям убить себя в прямом эфире через неделю\r\n\r\nМакс /Уильям Холден/ - лучший друг Говарда и руководитель отдела новостей в телекомпании...",
      "date": "2023-07-28T05:11:10.000Z",
      "author": "olga_o_kino",
      "userRating": 307,
      "authorId": 64776295,
      "createdAt": "2023-12-17T13:50:45.704Z",
      "updatedAt": "2024-02-28T14:25:13.379Z"
    },
    {
      "id": 3145210,
      "authorId": 22928730,
      ...
    }
  ],
  "total": 28,
  "limit": 10,
  "page": 1,
  "pages": 3
}
### Получение списка фильмов (GET):
Запрос:
curl -X 'GET' \
  'https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&selectFields=name&selectFields=alternativeName&selectFields=description&selectFields=shortDescription&selectFields=slogan&selectFields=type&selectFields=isSeries&selectFields=status&selectFields=year&selectFields=rating&selectFields=ageRating&selectFields=votes&selectFields=movieLength&selectFields=seriesLength&selectFields=totalSeriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=networks&selectFields=persons&selectFields=top250&type=' \
  -H 'accept: application/json' \
  -H 'X-API-KEY: {your_api_key}'


Ответ:
{
  "docs": [
    {
      "status": null,
      "rating": {
        "kp": 8.823,
        "imdb": 8.5,
        "filmCritics": 6.8,
        "russianFilmCritics": 100,
        "await": null
      },
      "votes": {
        "kp": 2006683,
        "imdb": 923505,
        "filmCritics": 129,
        "russianFilmCritics": 12,
        "await": 15
      },
      "movieLength": 112,
      "type": "movie",
      "name": "1+1",
      "description": "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
      "slogan": "Sometimes you have to reach into someone else's world to find out what's missing in your own.",
      "year": 2011,
      "poster": {
        "url": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/orig",
        "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/x1000"
      },
      "genres": [
        {
          "name": "драма"
        },
        {
          "name": "комедия"
        },
        {
          "name": "биография"
        }
      ],
      "countries": [
        {
          "name": "Франция"
        }
      ],
      "persons": [
        {
          "id": 71427,
          "photo": "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_71427.jpg",
          "name": "Франсуа Клюзе",
          "enName": "François Cluzet",
          "description": "Philippe",
          "profession": "актеры",
          "enProfession": "actor"
        },
        {
        ...
        },
        ]
    }
  ],
  "total": 1056229,
  "limit": 10,
  "page": 1,
  "pages": 105623
}
