package org.cleanpojo.ikkon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.cleanpojo.ikkon.fixtures.Generator.create;

import org.cleanpojo.ikkon.fixtures.*;
import org.junit.Test;

public class Mapper_specs {

    @Test
    public void correctly_maps_immutable_object_to_mutable_object() {
        var source = create(Immutable.class);
        var sut = new Mapper();

        Mutable actual = sut.map(source, Mutable.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getName()).isEqualTo(source.getName());
    }

    @Test
    public void correctly_maps_mutable_object_to_immutable_object() {
        var source = create(Mutable.class);
        var sut = new Mapper();

        Immutable actual = sut.map(source, Immutable.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getName()).isEqualTo(source.getName());
    }

    @Test
    public void fails_if_destination_type_has_multiple_constructors() {
        var source = create(Mutable.class);
        var sut = new Mapper();

        Throwable thrown = catchThrowable(() -> sut.map(source, MultipleConstructors.class));

        assertThat(thrown)
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining(MultipleConstructors.class.getName());
    }

    @Test
    public void correctly_maps_immutable_iterable_property() {
        var source = create(ImmutableIterableProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, ImmutableIterableProperty.class);

        assertThat(actual.getValues()).isNotSameAs(source.getValues());
        assertThat(actual.getValues()).isEqualTo(source.getValues());
    }

    @Test
    public void correctly_maps_iterable_property_to_immutable_collection_property() {
        var source = create(ImmutableIterableProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, ImmutableCollectionProperty.class);

        assertThat(actual.getValues()).isNotSameAs(source.getValues());
        assertThat(actual.getValues()).isEqualTo(source.getValues());
    }

    @Test
    public void correctly_maps_iterable_property_to_immutable_list_property() {
        var source = create(ImmutableIterableProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, ImmutableListProperty.class);

        assertThat(actual.getValues()).isNotSameAs(source.getValues());
        assertThat(actual.getValues()).isEqualTo(source.getValues());
    }

    @Test
    public void correctly_maps_mutable_iterable_property() {
        var source = create(MutableIterableProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, MutableIterableProperty.class);

        assertThat(actual.getValues()).isNotSameAs(source.getValues());
        assertThat(actual.getValues()).isEqualTo(source.getValues());
    }

    @Test
    public void supports_is_prefix_for_boolean_property() {
        var source = create(Freezable.class);
        var sut = new Mapper();

        var actual = sut.map(source, Freezable.class);

        assertThat(actual.isFrozen()).isEqualTo(source.isFrozen());
    }

    @Test
    public void correctly_maps_complex_object_property() {
        var source = create(ComplexObjectProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, ComplexObjectProperty.class);

        assertThat(actual.getChild()).isNotSameAs(source.getChild());
        assertThat(actual.getChild()).isEqualToComparingFieldByField(source.getChild());
    }

    @Test
    public void supports_ConstructorProperties_annotation() {
        var source = create(Immutable.class);
        var sut = new Mapper();

        var actual = sut.map(source, DecoratedWithConstructorProperties.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(source.getId());
        assertThat(actual.getName()).isEqualTo(source.getName());
    }

    @Test
    public void ignores_method_decorated_with_invalid_is_prefix() {
        var source = create(InvalidIsPrefix.class);
        var sut = new Mapper();

        var actual = sut.map(source, InvalidIsPrefix.class);

        assertThat(actual.isValue()).isNull();
    }

    @Test
    public void excepts_non_query_get_methods() {
        var source = create(NonQueryGetMethod.class);
        var sut = new Mapper();

        var actual = sut.map(source, Mutable.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isNull();
    }

    @Test
    public void excepts_get_query_methods_with_parameters() {
        var source = create(GetMethodWithParameter.class);
        var sut = new Mapper();

        var actual = sut.map(source, Immutable.class);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isNull();
    }

    @Test
    public void excepts_is_query_methods_with_parameters() {
        var source = create(IsMethodWithParameter.class);
        var sut = new Mapper();

        var actual = sut.map(source, Freezable.class);

        assertThat(actual).isNotNull();
        assertThat(actual.isFrozen()).isFalse();
    }

    @Test
    public void correctly_converts_property_to_destination_type() {
        var source = create(ComplexObjectProperty.class);
        var sut = new Mapper();

        var actual = sut.map(source, MutableComplexObjectProperty.class);

        assertThat(actual).usingRecursiveComparison().isEqualTo(source);
    }
}
