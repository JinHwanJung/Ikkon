package org.cleanpojo.ikkon.specs.flattening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cleanpojo.ikkon.specs.Generator.create;

import java.util.UUID;

import org.cleanpojo.ikkon.Mapper;
import org.junit.Test;

public class FlatteningSpecs {

    @Test
    public void correctly_flatten_to_immutable_complex_object() {
        // Arrange
        var source = create(ImmutableOrderModel.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, ImmutableOrderRecord.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());

        ImmutableAddressModel shippingAddress = source.getShippingAddress();
        assertThat(actual.getShippingAddressCountry()).isEqualTo(shippingAddress.getCountry());
        assertThat(actual.getShippingAddressState()).isEqualTo(shippingAddress.getState());
        assertThat(actual.getShippingAddressCity()).isEqualTo(shippingAddress.getCity());
        assertThat(actual.getShippingAddressZipCode()).isEqualTo(shippingAddress.getZipCode());
    }

    @Test
    public void correctly_flatten_null_to_immutable_complex_object() {
        var source = new ImmutableOrderModel(UUID.randomUUID(), null);
        var sut = new Mapper();

        var actual = sut.map(source, ImmutableOrderRecord.class);

        assertThat(actual.getShippingAddressCountry()).isNull();
        assertThat(actual.getShippingAddressState()).isNull();
        assertThat(actual.getShippingAddressCity()).isNull();
        assertThat(actual.getShippingAddressZipCode()).isNull();
    }

    @Test
    public void correctly_flatten_to_mutable_complex_object() {
        // Arrange
        var source = create(ImmutableOrderModel.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, MutableOrderRecord.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());

        ImmutableAddressModel shippingAddress = source.getShippingAddress();
        assertThat(actual.getShippingAddressCountry()).isEqualTo(shippingAddress.getCountry());
        assertThat(actual.getShippingAddressState()).isEqualTo(shippingAddress.getState());
        assertThat(actual.getShippingAddressCity()).isEqualTo(shippingAddress.getCity());
        assertThat(actual.getShippingAddressZipCode()).isEqualTo(shippingAddress.getZipCode());
    }

    @Test
    public void correctly_flatten_deep_immutable_complex_object() {
        // Arrange
        var source = create(ImmutablePaymentModel.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, ImmutablePaymentRecord.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getPaymentMethod()).isEqualTo(source.getPaymentMethod());

        ImmutableOrderModel order = source.getOrder();
        assertThat(actual.getOrderId()).isEqualTo(order.getId());

        ImmutableAddressModel shippingAddress = order.getShippingAddress();
        assertThat(actual.getOrderShippingAddressCountry()).isEqualTo(shippingAddress.getCountry());
        assertThat(actual.getOrderShippingAddressState()).isEqualTo(shippingAddress.getState());
        assertThat(actual.getOrderShippingAddressCity()).isEqualTo(shippingAddress.getCity());
        assertThat(actual.getOrderShippingAddressZipCode()).isEqualTo(shippingAddress.getZipCode());
    }

    @Test
    public void correctly_flatten_deep_mutable_complex_object() {
        // Arrange
        var source = create(ImmutablePaymentModel.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, MutablePaymentRecord.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getPaymentMethod()).isEqualTo(source.getPaymentMethod());

        ImmutableOrderModel order = source.getOrder();
        assertThat(actual.getOrderId()).isEqualTo(order.getId());

        ImmutableAddressModel shippingAddress = order.getShippingAddress();
        assertThat(actual.getOrderShippingAddressCountry()).isEqualTo(shippingAddress.getCountry());
        assertThat(actual.getOrderShippingAddressState()).isEqualTo(shippingAddress.getState());
        assertThat(actual.getOrderShippingAddressCity()).isEqualTo(shippingAddress.getCity());
        assertThat(actual.getOrderShippingAddressZipCode()).isEqualTo(shippingAddress.getZipCode());
    }

    @Test
    public void correctly_unflatten_to_immutable_complex_object() {
        // Arrange
        var source = create(ImmutableOrderRecord.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, ImmutableOrderModel.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());

        ImmutableAddressModel shippingAddress = actual.getShippingAddress();
        assertThat(shippingAddress.getCountry()).isEqualTo(source.getShippingAddressCountry());
        assertThat(shippingAddress.getState()).isEqualTo(source.getShippingAddressState());
        assertThat(shippingAddress.getCity()).isEqualTo(source.getShippingAddressCity());
        assertThat(shippingAddress.getZipCode()).isEqualTo(source.getShippingAddressZipCode());
    }

    @Test
    public void correctly_unflatten_to_mutable_complex_object() {
        // Arrange
        var source = create(ImmutableOrderRecord.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, MutableOrderModel.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());

        ImmutableAddressModel shippingAddress = actual.getShippingAddress();
        assertThat(shippingAddress.getCountry()).isEqualTo(source.getShippingAddressCountry());
        assertThat(shippingAddress.getState()).isEqualTo(source.getShippingAddressState());
        assertThat(shippingAddress.getCity()).isEqualTo(source.getShippingAddressCity());
        assertThat(shippingAddress.getZipCode()).isEqualTo(source.getShippingAddressZipCode());
    }

    @Test
    public void correctly_unflatten_to_deep_immutable_complex_object() {
        // Arrange
        var source = create(ImmutablePaymentRecord.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, ImmutablePaymentModel.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getPaymentMethod()).isEqualTo(source.getPaymentMethod());

        ImmutableOrderModel order = actual.getOrder();
        assertThat(order.getId()).isEqualTo(source.getOrderId());

        ImmutableAddressModel shippingAddress = order.getShippingAddress();
        assertThat(shippingAddress.getCountry()).isEqualTo(source.getOrderShippingAddressCountry());
        assertThat(shippingAddress.getState()).isEqualTo(source.getOrderShippingAddressState());
        assertThat(shippingAddress.getCity()).isEqualTo(source.getOrderShippingAddressCity());
        assertThat(shippingAddress.getZipCode()).isEqualTo(source.getOrderShippingAddressZipCode());
    }

    @Test
    public void correctly_unflatten_to_deep_mutable_complex_object() {
        // Arrange
        var source = create(ImmutablePaymentRecord.class);
        var sut = new Mapper();

        // Act
        var actual = sut.map(source, MutablePaymentModel.class);

        // Assert
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getPaymentMethod()).isEqualTo(source.getPaymentMethod());

        ImmutableOrderModel order = actual.getOrder();
        assertThat(order.getId()).isEqualTo(source.getOrderId());

        ImmutableAddressModel shippingAddress = order.getShippingAddress();
        assertThat(shippingAddress.getCountry()).isEqualTo(source.getOrderShippingAddressCountry());
        assertThat(shippingAddress.getState()).isEqualTo(source.getOrderShippingAddressState());
        assertThat(shippingAddress.getCity()).isEqualTo(source.getOrderShippingAddressCity());
        assertThat(shippingAddress.getZipCode()).isEqualTo(source.getOrderShippingAddressZipCode());
    }
}
