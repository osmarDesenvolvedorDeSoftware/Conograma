# Meu Cronograma

![Badge Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-brightgreen)
![License](https://img.shields.io/badge/Licen%C3%A7a-MIT-blue)

**Meu Cronograma** é um aplicativo Android desenvolvido em **Kotlin** com o objetivo de auxiliar no gerenciamento diário de tarefas, produtividade e aprendizado. O app permite o acompanhamento de desempenho por meio de gráficos e estatísticas, além de possibilitar a exportação de registros para **PDF**.

## 📌 Funcionalidades

✅ **Checklist diário** para controle de atividades concluídas<br>
✅ **Registro de aprendizado** para anotações diárias<br>
✅ **Gráficos interativos** de produtividade com **MPAndroidChart**<br>
✅ **Histórico de desempenho** armazenado localmente<br>
✅ **Exportação de dados** para PDF

## 🛠️ Tecnologias Utilizadas

- **Kotlin** - Linguagem principal
- **MPAndroidChart** - Biblioteca para gráficos
- **RecyclerView** - Exibição de listas
- **SharedPreferences** - Armazenamento local de dados
- **AndroidX** - Componentes modernos do Android

## 📸 Capturas de Tela

### 📊 Tela de Desempenho
<img src="https://github.com/osmarDesenvolvedorDeSoftware/Conograma/blob/master/desempenho.png" width="300">

### ✅ Tela de Checklist
<img src="https://github.com/osmarDesenvolvedorDeSoftware/Conograma/blob/master/checklist.png" width="300">

### 📖 Diário de Aprendizado
<img src="https://github.com/osmarDesenvolvedorDeSoftware/Conograma/blob/master/diario.png" width="300">

## 🚀 Como Rodar o Projeto

1. Clone este repositório:
   ```sh
   git clone https://github.com/osmarDesenvolvedorDeSoftware/MeuCronograma.git
   ```
2. Abra o projeto no **Android Studio**
3. Execute o aplicativo em um emulador ou dispositivo físico

## 📄 Estrutura do Projeto

```
MeuCronograma/
├── app/src/main/java/com/osmardev/meucronograma/
│   ├── ui/                 # Activities e UI
│   ├── model/              # Modelos de dados
│   ├── adapters/           # Adaptadores RecyclerView
│   ├── utils/              # Funções utilitárias
│   └── repository/         # Gerenciamento de dados
│
├── app/src/main/res/layout/
│   ├── activity_main.xml   # Tela principal
│   ├── activity_desempenho.xml # Tela de desempenho
│   ├── item_desempenho.xml # Layout do item de desempenho
│   └── activity_diario.xml # Tela do diário
```

## 📜 Licença

Este projeto está sob a licença **MIT** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

💡 **Dúvidas ou sugestões?** Fique à vontade para abrir uma **issue** ou contribuir com o projeto! 😃
