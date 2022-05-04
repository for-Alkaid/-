package zjut.qll.covidproject.domain.graphMock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphMock {

    private PersonPool personPool;

    private Hospital hospital;

    private City city;

    private PeopleInfo peopleInfo;

}

