package com.jc.interview;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example {


    public static void main(String[] args) {
        Customer customer=new Customer();
        customer.custId="1234";

        Supplier<AbstractMap.SimpleEntry<LocalDate,Integer>> s=new Supplier<AbstractMap.SimpleEntry<LocalDate,Integer>>() {
            int month=7;
            int date=1;
            @Override
            public AbstractMap.SimpleEntry<LocalDate,Integer> get() {
                return new AbstractMap.SimpleEntry<>(LocalDate.of(2019, (++month)%12==0?12:(month)%12, date++),new Random().nextInt(50));
            }
        };
        customer.points = Stream.generate(s).limit(20).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,AbstractMap.SimpleEntry::getValue));

        int rewardsPoints = customer.points.entrySet().stream()
                .filter(localDateIntegerEntry -> localDateIntegerEntry.getKey().isAfter(LocalDate.now().minusMonths(2)))
                .mapToInt(Map.Entry::getValue)
                .sum();

        System.out.println(rewardsPoints);
    }



}

class Customer{
    String custId;
    Map<LocalDate, Integer> points;

    @Override
    public String toString() {
        return "Customer{" +
                "custId='" + custId + '\'' +
                ", points=" + points +
                '}';
    }
}
