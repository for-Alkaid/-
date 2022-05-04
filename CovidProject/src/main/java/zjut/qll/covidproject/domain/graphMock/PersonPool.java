package zjut.qll.covidproject.domain.graphMock;

import lombok.Data;
import zjut.qll.covidproject.constant.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class PersonPool {

    private City city;

    List<Person> personList = new ArrayList<>();


    private static PersonPool personPool = new PersonPool();

    public static PersonPool getInstance() {
        return personPool;
    }


    /**
     * @param state 市民类型 Person.State的值，若为-1则返回当前总数目
     * @return 获取指定人群数量
     */
    public int getPeopleSize(int state) {
        if (state == -1) {
            return personList.size();
        }
        int i = 0;
        for (Person person : personList) {
            if (person.getState() == state) {
                i++;
            }
        }
        return i;
    }


    private PersonPool() {
        this.city = new City(500, 400);//设置城市中心为坐标(500,400)
        //添加城市居民
        for (int i = 0; i < Constants.CITY_PERSON_SIZE; i++) {
            Random random = new Random();
            //产生N(a,b)的数：Math.sqrt(b)*random.nextGaussian()+a
            int x = (int) (100 * random.nextGaussian() + city.getCenterX());
            int y = (int) (100 * random.nextGaussian() + city.getCenterY());
            if (x > Constants.CITY_WIDTH) {
                x = Constants.CITY_WIDTH;
            }
            personList.add(new Person(city, x, y));
        }
    }
}
