document.getElementById('pessoaForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('id').value;
    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;
    const email = document.getElementById('email').value;
    const dataNascimento = document.getElementById('dataNascimento').value;

    const pessoa = { nome, cpf, email, dataNascimento };

    if (id) {

        updatePessoaPut(id, pessoa);

    } else {
        createPessoa(pessoa);
    }
});

function getAllPessoas() {
    fetch('/pessoas', {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#pessoasTable tbody');
            tableBody.innerHTML = '';

            data.forEach(pessoa => {
                const row = document.createElement('tr');

                row.innerHTML = `
                <td>${pessoa.id}</td>
                <td>${pessoa.nome}</td>
                <td>${pessoa.cpf}</td>
                <td>${pessoa.email}</td>
                <td>${pessoa.dataNascimento}</td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editPessoa(${pessoa.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deletePessoa(${pessoa.id})">Excluir</button>
                </td>
            `;

                tableBody.appendChild(row);
            });

            // Atualiza o contador após obter todas as pessoas
            countPessoas();
        })
        .catch(error => {
            console.error('Erro ao obter pessoas:', error);
        });
}

function createPessoa(pessoa) {
    fetch('/pessoas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pessoa)
    })
        .then(response => response.json())
        .then(data => {
            alert('Pessoa criada com sucesso!');
            getAllPessoas();
        })
        .catch(error => {
            console.error('Erro ao criar pessoa:', error);
        });
}

function updatePessoaPut(id, pessoa) {
    fetch(`/pessoas/${id}`, {
        method: 'PUT', // Método PUT para atualização completa
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pessoa)
    })
        .then(response => response.json())
        .then(data => {
            alert('Pessoa atualizada com sucesso (PUT)!');
            getAllPessoas();
        })
        .catch(error => {
            console.error('Erro ao atualizar pessoa (PUT):', error);
        });
}

function updatePessoaPatch(id, pessoa) {
    fetch(`/pessoas/${id}`, {
        method: 'PATCH', // Método PATCH para atualização parcial
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pessoa)
    })
        .then(response => response.json())
        .then(data => {
            alert('Pessoa atualizada com sucesso (PATCH)!');
            getAllPessoas();
        })
        .catch(error => {
            console.error('Erro ao atualizar pessoa (PATCH):', error);
        });
}

function deletePessoa(id) {
    fetch(`/pessoas/${id}`, {
        method: 'DELETE'
    })
        .then(() => {
            alert('Pessoa excluída com sucesso!');
            getAllPessoas();
        })
        .catch(error => {
            console.error('Erro ao excluir pessoa:', error);
        });
}

function editPessoa(id) {
    fetch(`/pessoas/${id}`, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(pessoa => {
            document.getElementById('id').value = pessoa.id;
            document.getElementById('nome').value = pessoa.nome;
            document.getElementById('cpf').value = pessoa.cpf;
            document.getElementById('email').value = pessoa.email;
            document.getElementById('dataNascimento').value = pessoa.dataNascimento;
        })
        .catch(error => {
            console.error('Erro ao buscar pessoa:', error);
        });
}

function countPessoas() {
    fetch('/pessoas', {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {

            const count = data.length;

            document.getElementById('pessoasCount').textContent = `Total de pessoas cadastradas: ${count}`;
        })
        .catch(error => {
            console.error('Erro ao contar pessoas:', error);
        });
}
