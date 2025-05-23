# Тестовое задание для Android разработчика. Новостное приложение.

## Описание

Было реализовано два экрана: экран со списком новостей, с функцией поиска и сортировки новостей.  
Экран соответствует фукнкциональным и нефункциональным требованиям.  
Загрузка данных происходит из внешнего API https://www.theguardian.com/international/rss.
При разработке архитектуры было решено следовать правилам Clean Architecture.  
Используемый архитектурный паттерн: MVVM.  
Приложение поддерживает светлую и тёмную темы. 

## Стек технологий

- Язык: kotlin
- Работа с сетью: Retrofit, OkHttp
- Многопоточность: Kotlin Coroutines/Flow
- View: XML
- Сериализаторы: Kotlinx Serialization
- Навигация: Fragment
- Архитектура: MVVM
- Загрузка изображений: Glide

## Скриншоты работы приложения

### Список новостей
<img src="screenshots/main_screen.png" alt="Список новостей. Главный экран" width="50%"/>

### Поиск по новостям
<img src="screenshots/search_query.png" alt="Поиск по новостям в БД" width="50%" />

### Сортировка по возрастанию даты
<img src="screenshots/increase_sorted_filter.png" alt="Сортировка по возрастанию даты" width="50%" />

### Состояние загрузки
<img src="screenshots/loading_state.png" alt="Состояние загрузки" width="50%" />

### Сообщение об ошибке

<p align="center">
  <img src="screenshots/notification_error.png" alt="Уведомление об ошибке на главном экране" width="45%"/>
  <img src="screenshots/notification_error_detail_screen.png" alt="Ошибка загрузки страницы со статьей" width="45%"/>
</p>
