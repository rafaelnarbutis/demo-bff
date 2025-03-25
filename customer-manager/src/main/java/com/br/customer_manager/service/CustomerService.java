package com.br.customer_manager.service;

import com.br.customer_manager.Exception.AddressNotFoundException;
import com.br.customer_manager.Exception.CustomerNotFoundException;
import com.br.customer_manager.domain.Customer;
import com.br.customer_manager.dto.CustomerDTO;
import com.br.customer_manager.repository.CustomerRepository;
import com.br.customer_manager.util.CustomerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ViaCepService viaCepService;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) throws AddressNotFoundException {
        logger.info("Criando um customer com e-mail: {}", customerDTO.getEmail());
        CustomerDTO addressViaCep = viaCepService.getPostalCode(customerDTO.getPostalCode());

        if (addressViaCep == null || addressViaCep.getStreet() == null) {
            throw new AddressNotFoundException("Endereço nao encontrado para o CEP informado");
        }

        if (!CustomerUtil.isAdult(customerDTO.getBirthDate())) {
            throw new IllegalArgumentException("O cliente deve ter mais de 18");
        }

        customerDTO.setStreet(addressViaCep.getStreet());
        customerDTO.setNeighborhood(addressViaCep.getNeighborhood());
        customerDTO.setCity(addressViaCep.getCity());
        customerDTO.setState(addressViaCep.getState());

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setPostalCode(customerDTO.getPostalCode());
        customer.setNumber(customerDTO.getNumber());
        customer.setState(customerDTO.getState());
        customer.setCity(customerDTO.getCity());
        customer.setStreet(customerDTO.getStreet());
        customer.setNeighborhood(customerDTO.getNeighborhood());

        logger.info("customer saved M=", customer.getNeighborhood());
        customer = customerRepository.save(customer);

        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setTelephone(customer.getTelephone());
            customerDTO.setBirthDate(customer.getBirthDate());
            customerDTO.setPostalCode(customer.getPostalCode());
            customerDTO.setNumber(customer.getNumber());
            customerDTO.setStreet(customer.getStreet());
            customerDTO.setNeighborhood(customer.getNeighborhood());
            customerDTO.setCity(customer.getCity());
            customerDTO.setState(customer.getState());

            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
    }

    public CustomerDTO findById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (!optionalCustomer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        Customer customer = optionalCustomer.get();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setTelephone(customer.getTelephone());
        customerDTO.setBirthDate(customer.getBirthDate());
        customerDTO.setPostalCode(customer.getPostalCode());
        customerDTO.setNumber(customer.getNumber());
        customerDTO.setStreet(customer.getStreet());
        customerDTO.setNeighborhood(customer.getNeighborhood());
        customerDTO.setCity(customer.getCity());
        customerDTO.setState(customer.getState());

        return customerDTO;
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        logger.info("Iniciando a atualização do cliente com ID: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

        if (customerDTO.getBirthDate() != null && !customerDTO.getBirthDate().equals(customer.getBirthDate())) {
            throw new IllegalArgumentException("Data de nascimento nao pode ser alterada");
        }

        updateCustomerFields(customer, customerDTO);

        if (customerDTO.getPostalCode() != null) {
            CustomerDTO addressViaCep = viaCepService.getPostalCode(customerDTO.getPostalCode());
            if (addressViaCep != null) {
                updateCustomerFields(customer, addressViaCep);
            }
        }

        customer = customerRepository.save(customer);

        customerDTO.setId(customer.getId());
        return customerDTO;
    }

    private void updateCustomerFields(Customer customer, CustomerDTO customerDTO) {
        if (customerDTO.getName() != null) customer.setName(customerDTO.getName());
        if (customerDTO.getEmail() != null) customer.setEmail(customerDTO.getEmail());
        if (customerDTO.getPassword() != null) customer.setPassword(customerDTO.getPassword());
        if (customerDTO.getTelephone() != null) customer.setTelephone(customerDTO.getTelephone());
        if (customerDTO.getPostalCode() != null) customer.setPostalCode(customerDTO.getPostalCode());
        if (customerDTO.getNumber() != null) customer.setNumber(customerDTO.getNumber());
        if (customerDTO.getState() != null) customer.setState(customerDTO.getState());
        if (customerDTO.getCity() != null) customer.setCity(customerDTO.getCity());
        if (customerDTO.getStreet() != null) customer.setStreet(customerDTO.getStreet());
        if (customerDTO.getNeighborhood() != null) customer.setNeighborhood(customerDTO.getNeighborhood());
    }
}
