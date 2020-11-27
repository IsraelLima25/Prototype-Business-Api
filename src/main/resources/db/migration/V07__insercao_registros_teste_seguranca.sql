-- Usuarios
INSERT INTO usuario (email,senha) VALUES ("ilima@gmail.com","@ngul@r0");
INSERT INTO usuario (email,senha) VALUES ("ifilho@gmail.com", "@ngul@r1");

-- Perfis
INSERT INTO perfil (descricao) VALUES ("ROLE_ADMIN");
INSERT INTO perfil (descricao) VALUES ("ROLE_CLIENT");

-- Usuarios Permissões
INSERT INTO usuario_perfil (codigo_usuario,codigo_perfil) VALUES (1,1);
INSERT INTO usuario_perfil (codigo_usuario,codigo_perfil) VALUES (2,2);



