-- Script to add admin account
-- Run this if you need to add the admin account to existing database

-- For bankproject database (as used in code)
USE bankproject;

-- Or for sanbank database (as in SQL file)
-- USE sanbank;

-- Add admin account
INSERT INTO `user` (`username`, `password`) VALUES
('admin', '12345678');

-- Verify the account was added
SELECT * FROM `user` WHERE username = 'admin';

