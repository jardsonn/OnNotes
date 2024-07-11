package com.jalloft.onnotes.data

import java.util.UUID

/**
 * Created by Jardson Costa on 26/05/2024.
 */
data class Note(
    val id: UUID?,
    val title: String?,
    val content: String?,
    val createdAt: String?
)
//
//
//val notes = listOf(
//    Note(
//        title = "Primeira Nota",
//        content = "Esta é a primeira nota, contendo informações detalhadas sobre o projeto que estamos desenvolvendo. O objetivo é criar uma aplicação robusta e eficiente que atenda às necessidades dos usuários e melhore a produtividade.",
//        createdAt = "2024-05-26T10:15:30"
//    ),
//    Note(
//        title = "Segunda Nota",
//        content = "Nesta segunda nota, discutimos as especificações técnicas do sistema, incluindo a arquitetura.",
//        createdAt = "2024-05-26T11:20:35"
//    ),
//    Note(
//        title = "Terceira Nota",
//        content = "Aqui estão os detalhes sobre o design da interface do usuário. A interface deve ser intuitiva, amigável e acessível, com um foco especial na experiência do usuário e na facilidade de navegação.",
//        createdAt = "2024-05-26T12:25:40"
//    ),
//    Note(
//        title = "Quarta Nota",
//        content = "Esta nota aborda a integração com APIs de terceiros. A integração deve ser segura e eficiente, garantindo que os dados sejam transferidos de forma confiável e que as dependências externas não comprometam a performance.",
//        createdAt = "2024-05-26T13:30:45"
//    ),
//    Note(
//        title = "Quinta Nota",
//        content = "Aqui estão as estratégias de teste que serão adotadas. Inclui testes unitários, de integração e de sistema para assegurar que todos os componentes funcionem corretamente e que a aplicação como um todo esteja livre de erros.",
//        createdAt = "2024-05-26T14:35:50"
//    ),
//    Note(
//        title = "Sexta Nota",
//        content = "Esta nota trata da implementação de medidas de segurança.",
//        createdAt = "2024-05-26T15:40:55"
//    ),
//    Note(
//        title = "Sétima Nota",
//        content = "Discutimos aqui as práticas de manutenção e atualização do sistema. É importante que o sistema seja fácil de manter, com documentação clara e procedimentos definidos para a aplicação de atualizações e correções.",
//        createdAt = "2024-05-26T16:45:00"
//    ),
//    Note(
//        title = "Oitava Nota",
//        content = "Nesta nota, exploramos as estratégias de backup e recuperação de desastres. Garantir que os dados possam ser recuperados em caso de falha é crucial para a continuidade do negócio e a confiança dos usuários.",
//        createdAt = "2024-05-26T17:50:05"
//    ),
//    Note(
//        title = "Nona Nota",
//        content = "Aqui estão os planos de deploy e monitoramento. A aplicação deve ser implantada de maneira controlada, com monitoramento contínuo para identificar e resolver problemas rapidamente, garantindo alta disponibilidade.",
//        createdAt = "2024-05-26T18:55:10"
//    ),
//    Note(
//        title = "Décima Nota",
//        content = "Finalmente, esta nota aborda o feedback dos usuários e a iteração contínua. É importante ouvir os usuários e ajustar a aplicação com base no feedback recebido, para garantir que a solução continue atendendo às suas necessidades.",
//        createdAt = "2024-05-26T20:00:15"
//    )
//)