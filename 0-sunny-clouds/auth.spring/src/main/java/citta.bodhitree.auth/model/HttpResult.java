package citta.bodhitree.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {

    private int code = 200;
    private String msg;
    private Object data;
}
