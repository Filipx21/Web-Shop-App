package pl.filip.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String producerName;

    @NotNull
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Producer producer = (Producer) obj;
        return Objects.equals(id, producer.id)
                && Objects.equals(producerName, producer.producerName)
                && Objects.equals(address, producer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, producerName, address);
    }

    @Override
    public String toString() {
        return "Producer{"
                + "id=" + id
                + ", producerName='" + producerName + '\''
                + ", address='" + address + '\''
                + '}';
    }
}