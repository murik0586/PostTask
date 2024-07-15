Данная работа - является лишь практикой Котлин
--
_____________________________________
_____________________________________

ООП: ОБЪЕКТЫ И КЛАССЫ
--
_____________________________________
_____________________________________

1.CLASS, DATA CLASS их свойства, сокращения, деструктуризация

2.Aвто-тесты к ним! 

3.Синглтоны.

В качестве примера возьмём ВКонтакте: https://vk.com/dev/objects/post

Задача: ОБЪЕКТЫ И КЛАССЫ
--
_______________________________________________________________________________
TASK 1: Success: Data-класс Post (и другие классы, которые могут быть вложены в Post). - минимум 10 полей.
Примечание: хотя бы одно поле/свойство - должно быть вложенным классом.

TASK 2: Success: Объект WallService, который хранит посты в массиве. 


TASK 3: Success: ФУНКЦИЯ СОЗДАНИЯ ЗАПИСИ и функция обновления записи в синглтоне WallService


TASK 4: WALL TESTS:


 1. На функцию добавления записи.
 
  *Который проверяет, что после добавления поста id стал отличным от 0
 
 2. На функцию обновления записи.
 
  *Изменяем пост с существующим id, возвращается true,
  
  *Изменяем пост с несуществующим id, возвращается false
_____________________________________
_____________________________________

  ООП: НАСЛЕДОВАНИЕ, ПЕРЕОПРЕДЕЛЕНИЕ, ИНТЕРФЕЙСЫ и NULLABLE
--
_____________________________________
_____________________________________

Задача №1. Nullable
--
Доработать первую задачу, чтобы в классе Post некоторые поля были Nullable.

По итогу:

1. Data-класс Post, внутри Post Nullable-свойства.


2. WallService, который хранит посты в массиве.


3. WallService должна оставаться функциональной. Авто-тесты должны проходить.



Задача №2. Attachments
--
1. Разобраться с вложениями у постов - attachments.


2. В данном массиве могут храниться объекты разной структуры.


3. Есть массив attachments, в котором лежат объекты (назовём их тип Attachment). 


В объектах массива есть поле type, а второе поле может быть различным (оно определяется на базе значения поля type: в первом случае Photo, во втором —Video).

Возможны два варианта:

1.сделать Attachment интерфейсом.

2.сделать Attachment абстрактным классом.

3.нужно добавить в Attachment поле type

4.после описать наследников Attachment которые уже будут хранить специфичные данные (фото, аудио, видео).
Эти данные оформить в виде отдельных классов - Audio, Video и так далее. 


По итогу: 

1. Attachment интерфейсом\абстрактный класс. Наследники. 5 типов вложений.


2. Добавить в класс Post массив из объектов Attachment


3. WallService должна оставаться функциональной. Авто-тесты должны проходить.

Задача №3. Sealed-классы
--

Не обязательно, просто для практики и изучения.

Предыдущую задачу можно решить с помощью запечатанных (sealed) классов.


Нужно почитать документацию и сделать Pull Request с реализацией на базе sealed-классов


WallService должна оставаться функциональной,т.е. авто-тесты должны проходить.

В качестве примера возьмём ВКонтакте: https://vk.com/dev/objects/post
_______________________________________________________________________________
Задачи: НАСЛЕДОВАНИЕ, ПЕРЕОПРЕДЕЛЕНИЕ, ИНТЕРФЕЙСЫ и NULLABLE
_______________________________________________________________________________
TASK 1: Nullable


TASK 2: Attachments


TASK 3: Sealed-классы

TASK 4: REFACTORING
