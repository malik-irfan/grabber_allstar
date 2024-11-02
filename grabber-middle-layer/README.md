String message = """
//            <INST>You are an AI assistant that can answer your questions. If you don't know
//            the answer, don't make suggestions just say "I don't know".</INST>
//            question: {input}
//            """;


private String myContent = """
Tom is a programmer working for Acme Programming.

            Tom is under paid.
            
            Bob is a programmer working for Acme Programming.
            
            Bob is paid more than Tom.
            
            Acme Programming is a consulting company that employs programmers.
            
            """;

docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres ankane/pgvector
