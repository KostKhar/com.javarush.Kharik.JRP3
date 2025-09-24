import java.util.List;

public class Solution {
    private Quest quest;

    public static void main(String[] args) {
        Question q1 = new Question("Скучно в автосервисе?", List.of(new Answer("Очень устал, хочу нового", q2), new Answer(" У меня все отлично", q)));
        Question q = new Question("Ты проиграл, начать сначала?",  List.of(new Answer("да", q1)));
        Question q2 = new Question("Нужно учиться каждый день минимум два часа. Готов?", List.of(new Answer(" Могу попробовать и 3", q3), new Answer("Два часа слишком много, лучше на диване с пивом", q)));
    }
}
